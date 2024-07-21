package movie.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import movie.db.DatabaseConnection;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginForm() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel usernameLabel = new JLabel("아이디 :");
        usernameLabel.setBounds(10, 30, 80, 25);
        add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 30, 160, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("비밀번호 :");
        passwordLabel.setBounds(10, 65, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 65, 160, 25);
        add(passwordField);

        loginButton = new JButton("로그인");
        loginButton.setBounds(10, 100, 100, 25);
        add(loginButton);

        registerButton = new JButton("회원가입");
        registerButton.setBounds(160, 100, 100, 25);
        add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (DatabaseConnection.validateUser(username, password)) {
                    JOptionPane.showMessageDialog(null, "로그인 성공");
                    dispose();
                    new MovieBookingApp(username).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "아이디 또는 패스워드 실패");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = JOptionPane.showInputDialog("이메일 입력: ");

                if (DatabaseConnection.registerUser(username, password, email)) {
                    JOptionPane.showMessageDialog(null, "회원가입 성공");
                } else {
                    JOptionPane.showMessageDialog(null, "회원가입 실패");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
