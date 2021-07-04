package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.OrganizationAccount;
import com.myprogect.mywarehouse.db.repository.OrganizationAccountRepository;
import com.myprogect.mywarehouse.service.dto.OrganizationAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationAccountServiceImpl implements OrganizationAccountService {
    @Autowired
    OrganizationAccountRepository repository;

    @Override
    public OrganizationAccountDTO byId(Long id) {
        Optional<OrganizationAccount> accountFromDB = repository.findById(id);

        return accountFromDB.map(value -> new OrganizationAccountDTO()
                            .setId(value.getId())
                            .setCodeOfPayer(value.getCodeOfPayer())
                            .setOrganizationName(value.getOrganizationName())
                            .setSettlementAccount(value.getSettlementAccount())
                            .setBankCode(value.getOrganizationBank().getBankCode())).orElse(null);
    }

    @Override
    public List<OrganizationAccountDTO> findAllRecords() {
        List<OrganizationAccountDTO> accounts = new ArrayList<>();
        for(OrganizationAccount organizationAccount : repository.findAll()){
            accounts.add(new OrganizationAccountDTO()
                    .setId(organizationAccount.getId())
                    .setCodeOfPayer(organizationAccount.getCodeOfPayer())
                    .setOrganizationName(organizationAccount.getOrganizationName())
                    .setSettlementAccount(organizationAccount.getSettlementAccount())
                    .setCodeOfPayer(organizationAccount.getCodeOfPayer())
                    .setBankCode(organizationAccount.getOrganizationBank().getBankCode()));
        }
        return accounts;
    }

    @Override
    public void saveDate(OrganizationAccountDTO organizationAccount) {
        OrganizationAccount newAccount = new OrganizationAccount();
        newAccount.setOrganizationName(organizationAccount.getOrganizationName())
                .setCodeOfPayer(organizationAccount.getCodeOfPayer())
                .setSettlementAccount(organizationAccount.getSettlementAccount())
                .setBank_id(organizationAccount.getBankCode());
        repository.save(newAccount);
    }

    @Override
    public OrganizationAccountDTO findByOrganizationName(String organizationName) {
        Optional<OrganizationAccount> organizationAccount = repository.findByOrganizationName(organizationName);

        return  organizationAccount.map(value -> new OrganizationAccountDTO()
                            .setOrganizationName(value.getOrganizationName()))
                            .orElse(new OrganizationAccountDTO().setOrganizationName(""));
    }

    @Override
    public  OrganizationAccountDTO findByCodeOfPayer(Long codeOfPayer) {
        Optional<OrganizationAccount> organizationAccount = repository.findByCodeOfPayer(codeOfPayer);

        return organizationAccount.map(value -> new OrganizationAccountDTO()
                            .setCodeOfPayer(value.getCodeOfPayer()))
                            .orElse(new OrganizationAccountDTO().setCodeOfPayer(0L));
    }

    @Override
    public  OrganizationAccountDTO findBySettlementAccount(Long settlementAccount) {
        Optional<OrganizationAccount> organizationAccount = repository.findBySettlementAccount(settlementAccount);

        return organizationAccount.map(value -> new OrganizationAccountDTO()
                            .setSettlementAccount(value.getSettlementAccount()))
                            .orElse(new OrganizationAccountDTO().setCodeOfPayer(0L));
    }
}
