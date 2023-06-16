package GUI_HR;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageBranches extends JFrame {
    private JButton newButton;
    private JPanel panel1;
    private JButton associateEmployeeButton;
    private JButton updateButton;
    private JButton viewTransitButton;
    private JButton showAllButton;
    private JButton backButton;

    public ManageBranches (){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1054, 592);

        // Specify the path to your image file
        String imagePath = "C:\\Users\\PC\\Documents\\GitHub\\ADSS_Group_H\\docs\\branches.jpg";

        // Create and set the custom panel as the content pane
        BackgroundImage backgroundPanel = new BackgroundImage(imagePath);
        setContentPane(backgroundPanel);

        backgroundPanel.add(newButton);
        backgroundPanel.add(updateButton);
        backgroundPanel.add(associateEmployeeButton);
        backgroundPanel.add(viewTransitButton);
        backgroundPanel.add(showAllButton);
        backgroundPanel.add(backButton);

        ButtonStyle.set(newButton);
        ButtonStyle.set(updateButton);
        ButtonStyle.set(associateEmployeeButton);
        ButtonStyle.set(viewTransitButton);
        ButtonStyle.set(showAllButton);
        ButtonStyle.setExit(backButton);

        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new form here
                // Create and show the ManageEmployees frame
                NewBranch NB = new NewBranch();
                NB.setVisible(true);
                NB.setup();

            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new form here
                // Create and show the ManageEmployees frame
                UpdateBranch UPB = new UpdateBranch();
                UPB.setVisible(true);
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new form here
                // Create and show the ManageEmployees frame
                HR_Manager HR = new HR_Manager();
                HR.setVisible(true);

                setVisible(false);
            }
        });
    }

}