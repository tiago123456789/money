package com.tiago.money.money.facade;

import com.tiago.money.money.bo.LancamentoBO;
import com.tiago.money.money.bo.UsuarioBO;
import com.tiago.money.money.config.property.MoneyProperty;
import com.tiago.money.money.email.bean.EmailBean;
import com.tiago.money.money.email.bean.MessageHtmlBean;
import com.tiago.money.money.email.service.EmailService;
import com.tiago.money.money.model.Lancamento;
import com.tiago.money.money.model.Usuario;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NotificadorLancamentoVencidoFacade {

    private static final String PERMISSAO = "ROLE_PESQUISAR_LANCAMENTO";
    
    @Autowired
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(NotificadorLancamentoVencidoFacade.class);
    
    @Autowired
    private LancamentoBO lancamentoBO;

    @Autowired
    private UsuarioBO usuarioBO;

    @Autowired
    private MoneyProperty moneyProperty;

    @Autowired
    private EmailService emailService;

    public void notificar() {
    	if (this.logger.isDebugEnabled()) {
    		this.logger.debug("Iniciando processo de envio de email com lançamentos vencidos.");
    	}
    	
        List<Usuario> destinatarios = this.usuarioBO.buscarComBasePermissao(PERMISSAO);
        
        if (destinatarios.isEmpty()) {
        	this.logger.warn("Não possui destinatários");
        	return;
        }
        
        List<Lancamento> lancamentos = this.lancamentoBO.buscarLancamentoVencidosAteDataAtual();
        
        if (lancamentos.isEmpty()) {
        	this.logger.warn("Não possui lançamentos vencidos.");
        }
        
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("lancamentos", lancamentos);

        List<String> emailsDestinatarios = destinatarios
                                                .stream()
                                                .map(usuario -> usuario.getEmail())
                                                .collect(Collectors.toList());

        MessageHtmlBean messageHtml = new MessageHtmlBean();
        messageHtml.setTemplateHtml("email/aviso-lancamento-vencido");
        messageHtml.setParametros(parametros);

        EmailBean emailBean = new EmailBean();
        emailBean.setSubject("Lancamentos vencidos!");
        emailBean.setRemetente(this.moneyProperty.getEmail().getUsername());
        emailBean.setDestinatarios(emailsDestinatarios);

        this.emailService.enviar(emailBean, messageHtml);
    }
}
