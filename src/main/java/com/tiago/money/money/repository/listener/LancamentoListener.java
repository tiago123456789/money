package com.tiago.money.money.repository.listener;

import com.tiago.money.money.MoneyApplication;
import com.tiago.money.money.config.property.MoneyProperty;
import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.storage.s3.factory.S3Factory;
import org.apache.commons.lang.StringUtils;

import javax.persistence.PostLoad;

public class LancamentoListener {

    @PostLoad
    public void loadingData(Lancamento lancamento) {
        if (StringUtils.isNotEmpty(lancamento.getAnexo())) {
            MoneyProperty moneyProperty = MoneyApplication.getBean(MoneyProperty.class);
            lancamento.setUrlAnexo(
                    S3Factory
                            .getUrlObject(moneyProperty.getS3().getBucketName(), lancamento.getAnexo())
            );
        }
    }
}
