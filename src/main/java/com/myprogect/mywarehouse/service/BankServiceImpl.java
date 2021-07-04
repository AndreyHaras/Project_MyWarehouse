package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.Bank;
import com.myprogect.mywarehouse.db.entity.OrganizationAccount;
import com.myprogect.mywarehouse.db.entity.PartnerAccount;
import com.myprogect.mywarehouse.db.repository.BankRepository;
import com.myprogect.mywarehouse.service.dto.BankDTO;
import com.myprogect.mywarehouse.service.dto.BankWithInformationDTO;
import com.myprogect.mywarehouse.service.dto.OrganizationAccountDTO;
import com.myprogect.mywarehouse.service.dto.PartnerAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;

    @Override
    public BankDTO byId(Long id) {
        Optional<Bank> bank = bankRepository.findById(id);

        return bank.map(value -> new BankDTO()
                .setId(value.getId())
                .setBankCode(value.getBankCode())
                .setBankName(value.getBankName())).orElse(new BankDTO());
    }

    @Override
    public BankDTO findByName(String nameOfBank) {
        Optional<Bank> bank = bankRepository.findByBankName(nameOfBank);
        return bank.map(value -> new BankDTO()
                .setBankName(value.getBankName()))
                .orElse(new BankDTO().setBankName(""));
    }

    @Override
    public BankDTO findByCode(Long bankCode) {
        Optional<Bank> bank = bankRepository.findByBankCode(bankCode);
        return bank.map(value -> new BankDTO()
                .setBankCode(value.getBankCode()))
                .orElse(new BankDTO().setBankCode(0L));
    }

    @Override
    public void saveData(BankDTO bankDTO) {
        Bank bankDB = new Bank()
                .setBankName(bankDTO.getBankName())
                .setBankCode(bankDTO.getBankCode());
        bankRepository.save(bankDB);
    }

    @Override
    public List<BankDTO> findAllRecord() {
        List<BankDTO> results = new ArrayList<>();
        for(Bank bankDB : bankRepository.findAll()){
            results.add(new BankDTO()
                    .setId(bankDB.getId())
                    .setBankName(bankDB.getBankName())
                    .setBankCode(bankDB.getBankCode()));
        }

        return results;
    }

    @Override
    public BankWithInformationDTO findAllInformationById(Long id){
        Optional<Bank> bank = bankRepository.findById(id);

        return bank.map(value -> new BankWithInformationDTO()
                .setId(value.getId())
                .setBankCode(value.getBankCode())
                .setBankName(value.getBankName())
                .setOrganizationAccounts(getOrganizationAcc(value))
                .setPartnerAccounts(getPartnerAcc(value))).orElse(new BankWithInformationDTO());

    }

    private List<OrganizationAccountDTO> getOrganizationAcc(Bank bank){
        List<OrganizationAccountDTO> organizationAccountDTOS = new ArrayList<>();
        if(bank.getOrganizationAccounts() == null){
             organizationAccountDTOS.add(new OrganizationAccountDTO());
             return organizationAccountDTOS;
        }
        for(OrganizationAccount organizationAccount : bank.getOrganizationAccounts()){
            organizationAccountDTOS.add(
                    new OrganizationAccountDTO()
                    .setId(organizationAccount.getId())
                    .setOrganizationName(organizationAccount.getOrganizationName())
                    .setCodeOfPayer(organizationAccount.getCodeOfPayer())
                    .setSettlementAccount(organizationAccount.getSettlementAccount())
            );
        }
        return organizationAccountDTOS;
    }

    private List<PartnerAccountDTO> getPartnerAcc(Bank bank){
        List<PartnerAccountDTO> partnerAccountDTOS = new ArrayList<>();
        if(bank.getPartnerAccounts() == null){
            partnerAccountDTOS.add(new PartnerAccountDTO());
            return partnerAccountDTOS;
        }
        for(PartnerAccount partnerAccount : bank.getPartnerAccounts()){
            partnerAccountDTOS.add(
                    new PartnerAccountDTO()
                    .setId(partnerAccount.getId())
                    .setOrganizationName(partnerAccount.getOrganizationName())
                    .setPartnerCode(partnerAccount.getPartnerCode())
                    .setCodeOfPayer(partnerAccount.getCodeOfPayer())
                    .setSettlementAccount(partnerAccount.getSettlementAccount())
            );
        }
        return partnerAccountDTOS;
    }
}
