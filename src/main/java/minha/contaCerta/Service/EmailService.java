package minha.contaCerta.Service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
@Service 
public class EmailService {
    
    private final String REMETENTE = "contacerta68@gmail.com";
    private final String ASSUNTO = "Recuperação de Senha — ContaCerta";

    private final JavaMailSender mailSender;

    public EmailService (JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async 
    public void enviarCodigoRecuperacao(String destinatario, String nome, String codigo) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setFrom(REMETENTE);
            helper.setTo(destinatario);
            helper.setSubject(ASSUNTO);
            helper.setText(construirCorpoHtml(nome, codigo), true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Falha ao enviar email de recuperação", e);
        }
    }


    private String construirCorpoHtml (String nome, String codigo) {
        return """
                <!DOCTYPE html>
                <html>
                <body style="margin:0; padding:0; background-color:#FFFAFA; font-family:Arial, Helvetica, sans-serif;">
                    <table width="100%%" cellpadding="0" cellspacing="0" style="padding:32px 0;">
                    <tr>
                        <td align="center">
                            <table width="480" cellpadding="0" cellspacing="0" style="background-color:#ffffff; border-radius:10px; overflow:hidden; box-shadow:0 2px 12px rgba(92,74,77,0.10); border-top:4px solid #5C4A4D;">
                                <tr>
                                    <td style="padding:28px 32px 8px;">
                                        <span style="color:#5C4A4D; font-size:19px; font-weight:bold; letter-spacing:0.3px;">ContaCerta</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding:8px 32px 32px;">
                                        <p style="font-size:16px; color:#5C4A4D; margin:16px 0;">Olá %s,</p>
                                        <p style="font-size:15px; color:#8a7a7d; margin:0 0 24px; line-height:1.6;">
                                            Recebemos um pedido de recuperação de senha para a tua conta.
                                            Usa o código abaixo para continuar:
                                        </p>
                                        <table width="100%%" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td align="center" style="padding:20px; background-color:#FFF5F6; border-radius:10px;">
                                                    <span style="font-size:30px; font-weight:bold; letter-spacing:8px; color:#5C4A4D;">%s</span>
                                                </td>
                                            </tr>
                                        </table>
                                        <p style="font-size:13px; color:#b3a3a6; margin:24px 0 0; line-height:1.5;">
                                            Válido por <strong style="color:#5C4A4D;">10 minutos</strong>. Se não foste tu, ignora este email com segurança.
                                        </p>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="background-color:#FBECEF; padding:14px; text-align:center;">
                                        <span style="font-size:11px; color:#a08e91;">Conta Certa — Angola, Luanda</span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    </table>
                </body>
                </html>
                """.formatted(nome, codigo);
    }
}
