package com.mycompany.btap.controller;

import com.mycompany.btap.model.User;
import com.mycompany.btap.service.UserService;
import com.mycompany.btap.view.LoginView;
import com.mycompany.btap.view.HomeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class cLogin implements ActionListener {

    private final LoginView loginView;
    private final UserService userService;

    public cLogin(LoginView loginView) {
        this.loginView = loginView;
        this.userService = new UserService();
        this.loginView.getLoginButton().addActionListener(this);
        this.loginView.getRegisterButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginView.getLoginButton()) {
            handleLogin();
        } else if (e.getSource() == loginView.getRegisterButton()) {
            // Logic to open the register view
            // This part remains the same
            this.loginView.dispose();
            new com.mycompany.btap.view.RegisterView().setVisible(true);
        }
    }

    private void handleLogin() {
        String username = loginView.getUsername();
        String password = new String(loginView.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginView, "Username and password cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Disable the button to prevent multiple clicks
        loginView.getLoginButton().setEnabled(false);
        loginView.getLoginButton().setText("Logging in...");

        // Use SwingWorker to perform the network call on a background thread
        new SwingWorker<User, Void>() {
            @Override
            protected User doInBackground() throws Exception {
                // This runs on a background thread
                return userService.loginUser(username, password);
            }

            @Override
            protected void done() {
                // This runs on the Event Dispatch Thread (EDT) after doInBackground() finishes
                try {
                    User user = get(); // Get the result from doInBackground()
                    JOptionPane.showMessageDialog(loginView, "Login Successful! Welcome " + user.getUsername());
                    loginView.dispose();
                    new HomeView().setVisible(true);
                } catch (Exception ex) {
                    // Handle exceptions from the background thread (e.g., login failed, network error)
                    JOptionPane.showMessageDialog(loginView, "Login Failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    // Re-enable the button and restore text
                    loginView.getLoginButton().setEnabled(true);
                    loginView.getLoginButton().setText("Login");
                }
            }
        }.execute();
    }
}
