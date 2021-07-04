package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.Bank;
import com.myprogect.mywarehouse.db.entity.ConsignmentNote;
import com.myprogect.mywarehouse.db.entity.PartnerAccount;
import com.myprogect.mywarehouse.db.repository.PartnerAccountRepository;
import com.myprogect.mywarehouse.service.dto.BankDTO;
import com.myprogect.mywarehouse.service.dto.ConsignmentNoteDTO;
import com.myprogect.mywarehouse.service.dto.PartnerAccountDTO;
import com.myprogect.mywarehouse.service.dto.PartnerAccountWithInformationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PartnerAccountServiceImpl implements PartnerAccountService {
    @Autowired
    PartnerAccountRepository partnerAccountRepository;

    @Override
    public PartnerAccountWithInformationDTO byId(Long id) {
        Optional<PartnerAccount> partnerAccount = partnerAccountRepository.findById(id);
        return partnerAccount.map(value -> new PartnerAccountWithInformationDTO()
                                .setId(value.getId())
                                .setPartnerCode(value.getPartnerCode())
                                .setOrganizationName(value.getOrganizationName())
                                .setSettlementAccount(value.getSettlementAccount())
                                .setCodeOfPayer(value.getCodeOfPayer())
                                .setConsignmentNotes(getConsignmentNote(value))
                                .setPartnerBank(getPartnerBank(value))).orElse(null);
    }

    @Override
    public List<PartnerAccountDTO> findAllRecord() {
        List<PartnerAccountDTO> partnerAccountDTOS = new ArrayList<>();
        for(PartnerAccount partner : partnerAccountRepository.findAll()){
            partnerAccountDTOS.add(
                    new PartnerAccountDTO()
                            .setId(partner.getId())
                            .setOrganizationName(partner.getOrganizationName())
                            .setPartnerCode(partner.getPartnerCode())
                            .setCodeOfPayer(partner.getCodeOfPayer())
                            .setSettlementAccount(partner.getSettlementAccount())
                            .setBankCode(partner.getPartnerBank().getBankCode())
            );
        }
        return partnerAccountDTOS;
    }

    @Override
    public List<PartnerAccountDTO> findOnlyNameAndId() {
        List<PartnerAccountDTO> partnerNameAndId = new ArrayList<>();
        for(PartnerAccount partner : partnerAccountRepository.findAll()){
            partnerNameAndId.add(
                    new PartnerAccountDTO()
                            .setId(partner.getId())
                            .setOrganizationName(partner.getOrganizationName())
            );
        }
        return partnerNameAndId;
    }

    @Override
    public PartnerAccountDTO saveData(PartnerAccountDTO partnerAccountDTO) {
        PartnerAccount partnerAccountToDB = new PartnerAccount()
                .setPartnerCode(partnerAccountDTO.getPartnerCode())
                .setOrganizationName(partnerAccountDTO.getOrganizationName())
                .setCodeOfPayer(partnerAccountDTO.getCodeOfPayer())
                .setSettlementAccount(partnerAccountDTO.getSettlementAccount())
                .setBank_id(partnerAccountDTO.getBankCode());
        partnerAccountRepository.save(partnerAccountToDB);
        return null;
    }

    @Override
    public PartnerAccountDTO findByPartnerCode(Long partnerCode) {
        Optional<PartnerAccount> partnerAccount = partnerAccountRepository.findByPartnerCode(partnerCode);

        return partnerAccount.map(value -> new PartnerAccountDTO()
                .setPartnerCode(value.getPartnerCode()))
                .orElse(new PartnerAccountDTO().setPartnerCode(0L));
    }

    @Override
    public PartnerAccountDTO findByOrganizationName(String organizationName) {
        Optional<PartnerAccount> partnerAccount = partnerAccountRepository.findByOrganizationName(organizationName);

        return partnerAccount.map(value -> new PartnerAccountDTO()
                .setOrganizationName(value.getOrganizationName()))
                .orElse(new PartnerAccountDTO().setOrganizationName(""));
    }

    @Override
    public PartnerAccountDTO findByCodeOfPayer(Long codeOfPayer) {
        Optional<PartnerAccount> partnerAccount = partnerAccountRepository.findByCodeOfPayer(codeOfPayer);

        return partnerAccount.map(value -> new PartnerAccountDTO()
                .setCodeOfPayer(value.getCodeOfPayer()))
                .orElse(new PartnerAccountDTO().setCodeOfPayer(0L));
    }

    @Override
    public PartnerAccountDTO findBySettlementAccount(Long settlementAccount) {
        Optional<PartnerAccount> partnerAccount = partnerAccountRepository.findBySettlementAccount(settlementAccount);

        return partnerAccount.map(value -> new PartnerAccountDTO()
                .setSettlementAccount(value.getSettlementAccount()))
                .orElse(new PartnerAccountDTO().setSettlementAccount(0L));
    }

    private List<ConsignmentNoteDTO> getAllConsignmentNotes(PartnerAccount partnerAccount){
        List<ConsignmentNoteDTO> consignmentNoteDTOList = new ArrayList<>();
        for(ConsignmentNote note : partnerAccount.getConsignmentNotes()){
                consignmentNoteDTOList.add(
                        new ConsignmentNoteDTO()
                                .setId(note.getId())
                                .setConsignmentNoteId(String.valueOf(note.getConsignmentNoteId()))
                                .setConsignmentNoteDate(String.valueOf(note.getConsignmentNoteDate()))
                );
        }
        return consignmentNoteDTOList;
    }

    private BankDTO getPartnerBank(PartnerAccount partnerAccount){
        Bank bank = partnerAccount.getPartnerBank();
        return new BankDTO()
                    .setId(bank.getId())
                    .setBankName(bank.getBankName())
                    .setBankCode(bank.getBankCode());
    }

    private List<ConsignmentNoteDTO> getConsignmentNote(PartnerAccount partnerAccount){
        List<ConsignmentNoteDTO> consignmentNoteDTOList = new ArrayList<>();
        for(ConsignmentNote note : partnerAccount.getConsignmentNotes()){
            consignmentNoteDTOList.add(new ConsignmentNoteDTO()
                        .setId(note.getId())
                        .setConsignmentNoteId(String.valueOf(note.getConsignmentNoteId()))
                        .setConsignmentNoteDate(String.valueOf(note.getConsignmentNoteDate())));
        }
        return consignmentNoteDTOList;
    }

}
