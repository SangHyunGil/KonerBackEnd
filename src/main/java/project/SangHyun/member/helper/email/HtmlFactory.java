package project.SangHyun.member.helper.email;

public class HtmlFactory {

    public static String getHtml(String url) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;background: #f1f1f1;display: flex;align-items: center;margin: 0 auto !important;padding: 0 !important;height: 100% !important;width: 100% !important;\">\n" +
                "<head style=\"-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">\n" +
                "    <meta charset=\"utf-8\" style=\"-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\"> <!-- utf-8 works for most cases -->\n" +
                "    <meta name=\"viewport\" content=\"width=device-width\" style=\"-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\"> <!-- Forcing initial-scale shouldn't be necessary -->\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" style=\"-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\"> <!-- Use the latest (edge) version of IE rendering engine -->\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\" style=\"-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">  <!-- Disable auto-scale in iOS 10 Mail entirely -->\n" +
                "    <title style=\"-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\"></title> <!-- The title tag shows in email notifications, like Android 4.4. -->\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n" +
                "    <link href=\"https://fonts.googleapis.com/css2?family=Mochiy+Pop+P+One&display=swap\" rel=\"stylesheet\">\n" +
                "    <!-- CSS Reset : BEGIN -->\n" +
                "    <!-- CSS Reset : END -->\n" +
                "    <!-- Progressive Enhancements : BEGIN -->\n" +
                "</head>\n" +
                "\n" +
                "<body width=\"100%\" style=\"margin: 0 auto !important;padding: 0 !important;mso-line-height-rule: exactly;background-color: #f1f1f1;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;background: #f1f1f1;display: flex;align-items: center;font-family: 'Lato', sans-serif;font-weight: 400;font-size: 15px;line-height: 1.8;color: rgba(0,0,0,.4);height: 100% !important;width: 100% !important;\">\n" +
                "   <center style=\"width: 100%;background-color: #f1f1f1;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">\n" +
                "    <div style=\"display: none;font-size: 1px;max-height: 0px;max-width: 0px;opacity: 0;overflow: hidden;mso-hide: all;font-family: sans-serif;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">\n" +
                "      &zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;\n" +
                "    </div>\n" +
                "    <div style=\"max-width: 600px;margin: 0 auto;border: 15px solid #FFBE58;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" class=\"email-container\">\n" +
                "       <!-- BEGIN BODY -->\n" +
                "      <table align=\"center\" role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" style=\"margin: auto;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;mso-table-lspace: 0pt !important;mso-table-rspace: 0pt !important;border-spacing: 0 !important;border-collapse: collapse !important;table-layout: fixed !important;\">\n" +
                "         <tr style=\"-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">\n" +
                "          <td valign=\"top\" class=\"bg_white\" style=\"padding: 3rem 0;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;background: #ffffff;mso-table-lspace: 0pt !important;mso-table-rspace: 0pt !important;\">\n" +
                "             <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;mso-table-lspace: 0pt !important;mso-table-rspace: 0pt !important;border-spacing: 0 !important;border-collapse: collapse !important;table-layout: fixed !important;margin: 0 auto !important;\">\n" +
                "                <tr style=\"-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">\n" +
                "                   <td class=\"logo\" style=\"text-align: center;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;mso-table-lspace: 0pt !important;mso-table-rspace: 0pt !important; font-family: 'Mochiy Pop P One';\">\n" +
                "                     <h1 style=\"-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;font-family: 'Lato', sans-serif;color: #000000;margin-top: 0;font-weight: 400;margin: 0;\"><a href=\"http://localhost:3000\" style=\"-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;text-decoration: none;color: #0049AF;font-size: 5rem;font-weight: 700;font-family: 'Mochiy Pop P One', sans-serif;\"><img src=\"https://koner-bucket.s3.ap-northeast-2.amazonaws.com/logo/KakaoTalk_20220128_143615435.png\" alt=\"logo-img\" /></a></h1>\n" +
                "                   </td>\n" +
                "                </tr>\n" +
                "             </table>\n" +
                "          </td>\n" +
                "         </tr><!-- end tr -->\n" +
                "          <tr>\n" +
                "            <td bgcolor=\"#ffffff\" style=\"\"> \n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"> \n" +
                "                 <tbody>\n" +
                "                  <tr> \n" +
                "                   <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 24px; text-align: center;\"> <b>?????? ????????? ?????? ??????</b> </td> \n" +
                "                  </tr> \n" +
                "                  <tr> \n" +
                "                   <td style=\" text-align: center; font-family: Arial, sans-serif;\"> <br><br>???????????????.<br><br>Koner ??????????????????.<br> <br> ?????? ????????? ?????? ????????? ????????? ??????????????????.</td> \n" +
                "                  </tr> \n" +
                "                 </tbody>\n" +
                "                </table> \n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <tr style=\"-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">\n" +
                "          <td valign=\"middle\" class=\"hero bg_white\" style=\"padding: 2em 0 4em 0;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;background: #ffffff;position: relative;z-index: 0;font-family: 'ONE-Mobile-POP', sans-serif;mso-table-lspace: 0pt !important;mso-table-rspace: 0pt !important;\">\n" +
                "            <table style=\"-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;mso-table-lspace: 0pt !important;mso-table-rspace: 0pt !important;border-spacing: 0 !important;border-collapse: collapse !important;table-layout: fixed !important;margin: 0 auto !important;\">\n" +
                "               <tr style=\"-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">\n" +
                "                  <td style=\"-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;mso-table-lspace: 0pt !important;mso-table-rspace: 0pt !important;\">\n" +
                "                     <div class=\"text\" style=\"padding: 0 2.5em;text-align: center;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;color: rgba(0,0,0,.3);\">\n" +
                "                        <p style=\"font-family: 'ONE-Mobile-POP',sans-serif; -ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\"><a href=\"" + url + "\" class=\"btn btn-primary\" style=\"-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;text-decoration: none;color: white;padding: 10px 15px;display: inline-block;border-radius: 5px;background: #FFBE58;\">????????? ????????????</a></p>\n" +
                "                     </div>\n" +
                "                  </td>\n" +
                "               </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "         </tr><!-- end tr -->\n" +
                "          <tr>\n" +
                "            <td bgcolor=\"#2F2F2F\" style=\"padding: 30px 30px 30px 30px;\"> \n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"> \n" +
                "                 <tbody>\n" +
                "                  <tr> \n" +
                "                   <td width=\"75%\" style=\"color:white; font-family: Arial, sans-serif;\">&copy;Copyright Koner all rights reserved. </td> \n" +
                "                  </tr> \n" +
                "                 </tbody>\n" +
                "            </table> </td>\n" +
                "          </tr>\n" +
                "    </table></div>\n" +
                "  </center>\n" +
                "</body>\n" +
                "</html>";
    }
}
