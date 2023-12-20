package com.ane.dynamodb.service.impl;

import com.ane.dynamodb.dto.CostumerDTO;
import com.ane.dynamodb.model.Costumer;
import com.ane.dynamodb.repository.CostumerRepository;
import com.ane.dynamodb.service.CostumerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CostumerServiceImpl implements CostumerService {

    private final CostumerRepository costumerRepository;

    public CostumerServiceImpl(CostumerRepository costumerRepository) {
        this.costumerRepository = costumerRepository;
    }

    @Override
    public Costumer saveCostumer(CostumerDTO costumerDTO) {
       if(costumerRepository.findByCompanyDocumentNumber(costumerDTO.getCompanyDocumentNumber()).isPresent()){
           throw new RuntimeException("There is already a customer with this document number");
       }
       return costumerRepository.save(costumerDTO.costumerDTOToCostumer());
    }

    @Override
    public List<Costumer> findAllCostumers() {
        return (List<Costumer>) costumerRepository.findAll();
    }

    @Override
    public List<Costumer> findByCompanyName(String companyName) {
        return costumerRepository.findByCompanyName(companyName);
    }

    /*@Override
    public Costumer updateCostumer(String companyDocument, CostumerDTO costumerDTO) {
        Optional<Costumer> costumer = costumerRepository.findByCompanyDocumentNumber(companyDocument);
        if(costumer.isEmpty()){
            throw new RuntimeException("There is no customer with this document number");
        }
        BeanUtils.copyProperties(costumerDTO, costumer.get(), "active", "id");
        return costumerRepository.save(costumer.get());
    }*/

    @Override
    public Costumer disableCostumer(String companyDocumentNumber) {
        Optional<Costumer> costumer = costumerRepository.findByCompanyDocumentNumber(companyDocumentNumber);

        if(costumer.isEmpty()){
            throw new RuntimeException("There is no customer with document number");
        }
        costumer.get().setActive(false);
        return costumerRepository.save(costumer.get());
    }
}
