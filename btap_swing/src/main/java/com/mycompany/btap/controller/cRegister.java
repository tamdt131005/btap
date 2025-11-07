package com.mycompany.btap.controller;

import com.mycompany.btap.service.UserService;
import com.mycompany.btap.view.RegisterView;
import com.mycompany.btap.view.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class cRegister implements ActionListener {

    private final RegisterView registerView;
    private final UserService userService;

    public cRegister(RegisterView registerView) {
        this.registerView = registerView;
        this.userService = new UserService();
        this.registerView.getRegisterButton().addActionListener(this);
        this.registerView.getLoginButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerView.getRegisterButton()) {
            handleRegister();
        } else if (e.getSource() == registerView.getLoginButton()) {
            // Logic to go back to the login view
            this.registerView.dispose();
            new LoginView().setVisible(true);
        }
    }

    private void handleRegister() {
        String username = registerView.getUsername();
        String email = registerView.getEmail();
        String password = new String(registerView.getPassword());
        String confirmPassword = new String(registerView.getConfirmPassword());

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(registerView, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(registerView, "Passwords do not match.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Disable the button
        registerView.getRegisterButton().setEnabled(false);
        registerView.getRegisterButton().setText("Registering...");

        // Use SwingWorker for the background task
        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                // This runs on a background thread
                return userService.registerUser(username, password, email);
            }

            @Override
            protected void done() {
                // This runs on the EDT after completion
                try {
                    String successMessage = get();
                    JOptionPane.showMessageDialog(registerView, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Go to login screen after successful registration
                    registerView.dispose();
                    new LoginView().setVisible(true);
                } catch (Exception ex) {
                    // Handle registration or network errors
                    JOptionPane.showMessageDialog(registerView, "Registration Failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    // Re-enable the button
                    registerView.getRegisterButton().setEnabled(true);
                    registerView.getRegisterButton().setText("Register");
                }
            }
        }.execute();
    }
}
