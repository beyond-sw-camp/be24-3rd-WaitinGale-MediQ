package org.example.mediqback.user;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public String sendWelcomeMail(String uuid, String email){
        MimeMessage message  = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            String subject = "[MediQ] 환영합니다! 이메일 인증을 완료해주세요.";
            String htmlContents = "<a href='http://localhost:8080/user/verify?uuid="+uuid+"'>이메일 인증</a>";
            helper.setSubject(subject);
            helper.setText(htmlContents, true);
            mailSender.send(message);

            return uuid;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendTempPassword(String email, String tempPassword) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("[MediQ] 임시 비밀번호가 발급되었습니다.");

            String htmlContents = "<h2>임시 비밀번호 안내</h2>"
                    + "<p>요청하신 임시 비밀번호는 <strong>" + tempPassword + "</strong> 입니다.</p>"
                    + "<p>로그인 후 마이페이지에서 반드시 비밀번호를 변경해 주세요.</p>";

            helper.setText(htmlContents, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("이메일 발송 중 오류가 발생했습니다.", e);
        }
    }
}