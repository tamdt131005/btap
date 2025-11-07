
package com.mycompany.btap;

import com.mycompany.btap.view.vLogin;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new vLogin();
            }
        });
    }
}
