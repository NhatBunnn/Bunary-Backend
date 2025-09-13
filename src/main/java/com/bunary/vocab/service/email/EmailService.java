package com.bunary.vocab.service.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService implements IEmailService {
    private final JavaMailSender mailSender;

    @Async
    public void sendVerificationCode(String toEmail, String code) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Your verification code");

            // Nội dung HTML
            String htmlContent = """
                                                                <div style="font-family: 'Segoe UI', Arial, sans-serif; background: #ffffff; padding: 30px; text-align: center; color: #333; max-width: 420px; margin: auto; border-radius: 16px; box-shadow: 0 4px 20px rgba(0,0,0,0.08);">

                        <h2 style="margin-bottom: 8px; font-size: 24px; font-weight: 600; color: #4A90E2;">Mã Xác Thực</h2>
                        <p style="margin: 0; font-size: 15px; color: #555;">Sử dụng mã dưới đây để xác thực tài khoản của bạn</p>

                        <button id="copyCodeBtn"
                                style="margin-top: 20px; padding: 10px 20px; background: linear-gradient(135deg, #4A90E2, #50E3C2);
                                       color: white; border: none; border-radius: 8px; font-size: 20px; font-weight: 600;
                                       letter-spacing: 4px; cursor: pointer; box-shadow: 0 4px 8px rgba(0,0,0,0.15); transition: transform 0.1s;">
                             %s
                        </button>

                        <div id="copyMsg" style="margin-top: 12px; font-size: 13px; color: #28a745; display: none;">
                            Bạn đã sao chép mã!
                        </div>

                        <div style="margin: 25px 0;">
                            <img src="https://i.pinimg.com/736x/89/f3/6c/89f36ce0ef58d2d23056fee39c18e3ee.jpg"
                                 alt="Xác Thực" style="width: 200px; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);"/>
                        </div>

                        <p style="font-size: 12px; color: #999; margin-top: 15px;">
                            Nếu bạn không yêu cầu mã này, bạn có thể bỏ qua email này.
                        </p>
                    </div>

                    <script>
                        const copyBtn = document.getElementById('copyCodeBtn');
                        const copyMsg = document.getElementById('copyMsg');

                        copyBtn.addEventListener('click', async () => {
                            const code = copyBtn.textContent.trim();

                            try {
                                if (navigator.clipboard && navigator.clipboard.writeText) {
                                    await navigator.clipboard.writeText(code);
                                } else {
                                    // fallback cho các trình duyệt cũ
                                    const textArea = document.createElement('textarea');
                                    textArea.value = code;
                                    document.body.appendChild(textArea);
                                    textArea.select();
                                    document.execCommand('copy');
                                    document.body.removeChild(textArea);
                                }

                                copyMsg.style.display = 'block';
                                copyBtn.style.transform = 'scale(0.95)';

                                setTimeout(() => {
                                    copyMsg.style.display = 'none';
                                    copyBtn.style.transform = 'scale(1)';
                                }, 1500);
                            } catch (err) {
                                alert('Không thể sao chép mã. Hãy thử lại!');
                                console.error(err);
                            }
                        });
                    </script>

                                                            """
                    .formatted(code);

            helper.setText(htmlContent, true); // true = HTML

            helper.setFrom("bunbohue2725@gmail.com");

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
