package doanltm;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Scott
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class ServerThread implements Runnable {

    private Scanner in = null;
    private PrintWriter out = null;
    private Socket socket;
    private String name;

    public ServerThread(Socket socket, String string) throws IOException {
        this.socket = socket;
        this.name = name;
        this.in = new Scanner(this.socket.getInputStream());
        this.out = new PrintWriter(this.socket.getOutputStream(), true);
        new Thread(this).start();
    }

    public void run() {

        try {
            while (true) {
                String chuoi = in.nextLine().trim();
                String khoa = in.nextLine().trim();
                System.out.println("Chuoi nhan: " + chuoi);
                Scanner sc = new Scanner(chuoi);
                //sc.useDelimiter("@");
                //int flag = sc.nextInt();
                int flag = in.nextInt();
                int rowsize = 26;
                int columnsize = 26;
                int vigneretable[][] = new int[26][26];
                for (int rows = 0; rows < rowsize; rows++) {
                    for (int column = 0; column < columnsize; column++) {
                        vigneretable[rows][column] = (rows + column) % 26;
                    }
                }
                if (flag == 1) {
                    try {
                        String vanban = sc.nextLine().trim();
                        System.out.println("Enter the plainttext");
                        vanban = vanban.toUpperCase();
                        System.out.println(vanban);
                        System.out.println("Enter the key: ");
                        System.out.println(khoa);
                        khoa = khoa.toUpperCase();
                        String cipher = "";
                        int keyindex = 0;
                        for (int index = 0; index < vanban.length(); index++) {
                            char pChar = vanban.charAt(index);
                            int asciiVal = (int) pChar;
                            if (pChar == ' ') {
                                cipher += pChar;
                                continue;
                            }
                            if (asciiVal < 65 || asciiVal > 90) {
                                cipher += pChar;
                                continue;
                            }
                            int basicPlaintTextValue = ((int) pChar) - 65;
                            char kChar = khoa.charAt(keyindex);
                            int basicKeyValue = ((int) kChar) - 65;
                            int tableEntry = vigneretable[basicPlaintTextValue][basicKeyValue];
                            char cChar = (char) (tableEntry + 65);
                            cipher += cChar;
                            keyindex++;
                            if (keyindex == khoa.length()) {
                                keyindex = 0;
                            }
                        }
                        System.out.println("Cipher text is " + cipher);
                        cipher.toUpperCase();
                        out.println(cipher);
                        // dem ky tu xuat hien nhieu nhat
                        int counter[] = new int[256];
                        ArrayList vt = new ArrayList();
                        int len = cipher.length();

                        int max = 0;
                        int so = 0, cout = 0;
                        String chuoi2 = cipher.replaceAll(" ", "");
                        System.out.println("chuoi: " + chuoi2);

                        int len1 = chuoi2.length();

                        for (int i = 0; i < len1; i++) {
                            //System.out.print(counter[plaint.charAt(i)]++ + "\t");
                            counter[chuoi2.charAt(i)]++;
                        }

                        char array[] = new char[chuoi2.length()];  // tạo mảng mới có độ dài bằng chuổi nhập

                        for (int i = 0; i < len1; i++) {

                            array[i] = chuoi2.charAt(i); // gán mảng giá trị của mảng a = ký tự chuỗi chuoi

                            int flagg = 0; // tạo cờ
                            for (int j = 0; j <= i; j++) {
                                if (chuoi2.charAt(i) == array[j]) {
                                    flagg++;
                                }
                            }
                            if (flag == 1) {

                                if (counter[chuoi2.charAt(i)] >= max) {

                                    max = counter[chuoi2.charAt(i)];
                                    so = i;
                                }

                            }
                        }

                        char kytu = chuoi2.charAt(so);
                        System.out.println("");
                        System.out.println("Ky tu xuat hien nhieu nhất: " + kytu);
                        out.println(kytu);
                        System.out.println("So lan xuat hien: " + max);
                        out.println(max);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Key phải là chữ!");
                    }

                } else if (flag == 2) {
                    try {
                        String vanban = sc.nextLine().trim();
                        System.out.println("Enter the cipher text");
                        String cipher = vanban.toUpperCase();
                        System.out.println("Enter the key: ");
                        String keychar = khoa.toUpperCase();
                        //keychar = keychar.toUpperCase();
                        String plaint = "";
                        int keyindex = 0;
                        for (int index = 0; index < cipher.length(); index++) {
                            char pChar = cipher.charAt(index);
                            int asciiVal = (int) pChar;
                            if (pChar == ' ') {
                                plaint += pChar;
                                continue;
                            }
                            if (asciiVal < 65 || asciiVal > 90) {
                                plaint += pChar;
                                continue;
                            }
                            int basicPlaintTextValue = ((int) pChar) - 65;
                            char kChar = keychar.charAt(keyindex);
                            int basicKeyValue = ((int) kChar) - 65;
                            for (int i = 0; i < columnsize; i++) {
                                if (vigneretable[basicKeyValue][i] == basicPlaintTextValue) {
                                    char potcChar = (char) (vigneretable[basicKeyValue][i] + 65);
                                    char potpChar = (char) (i + 65);
                                    plaint += potpChar;
                                }
                            }
                            keyindex++;
                            if (keyindex == keychar.length()) {
                                keyindex = 0;
                            }
                        }
                        System.out.println("plaint text is " + plaint);
                        plaint.toUpperCase();
                        out.println(plaint);
                        cipher.toUpperCase();
                        out.println(cipher);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Key phải là chữ!");
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(name + " has departed");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }

}
