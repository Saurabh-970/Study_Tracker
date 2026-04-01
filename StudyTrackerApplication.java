import java.util.*;
import java.time.LocalDate;
import java.io.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// ================= MODEL CLASS =================
class Study_Log
{
    private LocalDate Date;
    private String Subject;
    private double Duration;
    private String Description;

    public Study_Log(LocalDate a , String b , double c, String d)
    {
        this.Date = a;
        this.Subject = b;
        this.Duration = c;
        this.Description = d;
    }

    public LocalDate getDate() { return this.Date; }
    public String getSubject() { return this.Subject; }
    public double getDuration() { return this.Duration; }
    public String getDescription() { return this.Description; }

    @Override
    public String toString()
    {
        return Date+" | "+Subject+" | "+Duration+" | "+Description;
    }
}

// ================= SERVICE CLASS =================
class Study_Tracker
{
    public ArrayList <Study_Log> Database = new ArrayList<>();

    public void InsertLog(String sub, double dur, String desc)
    {
        Study_Log studyobj = new Study_Log(LocalDate.now(), sub, dur, desc);
        Database.add(studyobj);
    }

    public void DisplayLog()
    {
        if(Database.isEmpty())
        {
            System.out.println("Nothing to Display");
            return;
        }

        for(Study_Log s: Database)
        {
            System.out.println(s);
        }
    }

    public void Export_CSV()
    {
        if(Database.isEmpty())
        {
            System.out.println("Nothing to Export");
            return;
        }

        try(FileWriter fwobj = new FileWriter("StudyTracker.csv"))
        {
            fwobj.write("Date,Subject,Duration,Description\n");

            for(Study_Log s : Database)
            {
                fwobj.write(s.getDate()+","+
                        s.getSubject()+","+
                        s.getDuration()+","+
                        s.getDescription()+"\n");
            }
            System.out.println("CSV Exported");
        }
        catch(Exception e)
        {
            System.out.println("Error in CSV");
        }
    }
}

// ================= GUI CLASS =================
class StudyTrackerGUI extends JFrame
{
    private JTextField txtSubject, txtDuration;
    private JTextArea txtDescription;
    private JTable table;
    private DefaultTableModel model;

    private Study_Tracker tracker;

    public StudyTrackerGUI(Study_Tracker tracker)
    {
        this.tracker = tracker;

        setTitle("📚 Study Tracker Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        // COLORS
        Color bgColor = new Color(245, 247, 250);
        Color primary = new Color(52, 152, 219);
        Color accent = new Color(46, 204, 113);

        getContentPane().setBackground(bgColor);

        // TOP PANEL
        JPanel top = new JPanel(new GridLayout(4,2,10,10));
        top.setBorder(BorderFactory.createTitledBorder("Add Study Log"));
        top.setBackground(bgColor);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        JLabel lbl1 = new JLabel("Subject:");
        lbl1.setFont(labelFont);
        top.add(lbl1);

        txtSubject = new JTextField();
        top.add(txtSubject);

        JLabel lbl2 = new JLabel("Duration (hrs):");
        lbl2.setFont(labelFont);
        top.add(lbl2);

        txtDuration = new JTextField();
        top.add(txtDuration);

        JLabel lbl3 = new JLabel("Description:");
        lbl3.setFont(labelFont);
        top.add(lbl3);

        txtDescription = new JTextArea(2,20);
        txtDescription.setLineWrap(true);
        top.add(new JScrollPane(txtDescription));

        JButton btnAdd = new JButton("➕ Add Log");
        btnAdd.setBackground(primary);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        top.add(btnAdd);

        add(top, BorderLayout.NORTH);

        // TABLE
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Date","Subject","Duration","Description"});

        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(primary);
        table.getTableHeader().setForeground(Color.WHITE);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // BOTTOM PANEL
        JPanel bottom = new JPanel();
        bottom.setBackground(bgColor);

        JButton btnView = new JButton("📊 View Logs");
        JButton btnExport = new JButton("📁 Export CSV");

        btnView.setBackground(accent);
        btnView.setForeground(Color.WHITE);
        btnExport.setBackground(primary);
        btnExport.setForeground(Color.WHITE);

        btnView.setFocusPainted(false);
        btnExport.setFocusPainted(false);

        bottom.add(btnView);
        bottom.add(btnExport);

        add(bottom, BorderLayout.SOUTH);

        // ACTIONS

        btnAdd.addActionListener(e -> {
            try {
                String sub = txtSubject.getText();
                double dur = Double.parseDouble(txtDuration.getText());
                String desc = txtDescription.getText();

                tracker.InsertLog(sub, dur, desc);

                JOptionPane.showMessageDialog(this, "✅ Log Added Successfully");

                txtSubject.setText("");
                txtDuration.setText("");
                txtDescription.setText("");

            } catch(Exception ex) 
            {
                JOptionPane.showMessageDialog(this, "❌ Invalid Input");
            }
        });

        btnView.addActionListener(e -> {
            model.setRowCount(0);

            for(Study_Log s : tracker.Database)
            {
                model.addRow(new Object[]{
                        s.getDate(),
                        s.getSubject(),
                        s.getDuration(),
                        s.getDescription()
                });
            }
        });

        btnExport.addActionListener(e -> {
            tracker.Export_CSV();
            JOptionPane.showMessageDialog(this, "📁 CSV Exported Successfully");
        });
    }
}

// ================= MAIN CLASS =================
public class StudyTrackerApplication
{
    public static void main(String args[])
    {
        Scanner sobj = new Scanner(System.in);
        Study_Tracker stobj = new Study_Tracker();

        System.out.println("1. CLI Mode");
        System.out.println("2. GUI Mode");
        int choice = sobj.nextInt();

        if(choice == 1)
        {
            int iCHOICE = 0;
            do
            {
                System.out.println("1 Insert | 2 View | 3 Export | 4 Exit");
                iCHOICE = sobj.nextInt();

                switch(iCHOICE)
                {
                    case 1:
                        sobj.nextLine();
                        System.out.println("Subject:");
                        String sub = sobj.nextLine();

                        System.out.println("Duration:");
                        double dur = sobj.nextDouble();
                        sobj.nextLine();

                        System.out.println("Description:");
                        String desc = sobj.nextLine();

                        stobj.InsertLog(sub, dur, desc);
                        break;

                    case 2:
                        stobj.DisplayLog();
                        break;

                    case 3:
                        stobj.Export_CSV();
                        break;
                }

            } while(iCHOICE != 4);
        }
        else
        {
            SwingUtilities.invokeLater(() -> {
                new StudyTrackerGUI(stobj).setVisible(true);
            });
        }
    }
}