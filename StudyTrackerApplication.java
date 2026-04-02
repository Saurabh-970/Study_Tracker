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

    public void Export_CSV()
    {
        try(FileWriter fwobj = new FileWriter("StudyTracker.csv"))
        {
            fwobj.write("Date,Subject,Duration,Description\n");

            for(Study_Log s : Database)
            {
                fwobj.write(s.getDate()+","+s.getSubject()+","+s.getDuration()+","+s.getDescription()+"\n");
            }
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

        setTitle("📚 Study Tracker");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🎨 DARK CLASSY COLORS
        Color bg = new Color(30, 32, 40);
        Color card = new Color(44, 47, 57);
        Color primary = new Color(0, 173, 181);
        Color accent = new Color(57, 62, 70);
        Color text = Color.WHITE;

        getContentPane().setBackground(bg);

        // ===== TITLE =====
        JLabel title = new JLabel("Study Tracker Dashboard", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(text);
        title.setBorder(BorderFactory.createEmptyBorder(15,0,15,0));
        add(title, BorderLayout.NORTH);

        // ===== MAIN PANEL =====
        JPanel main = new JPanel(new BorderLayout(15,15));
        main.setBackground(bg);
        main.setBorder(BorderFactory.createEmptyBorder(10,15,15,15));

        // ===== FORM =====
        JPanel form = new JPanel(new GridLayout(4,2,10,10));
        form.setBackground(card);
        form.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(primary), "Add Study Log"));

        Font f = new Font("Segoe UI", Font.BOLD, 14);

        JLabel l1 = new JLabel("Subject:");
        l1.setForeground(text);
        l1.setFont(f);
        form.add(l1);

        txtSubject = new JTextField();
        form.add(txtSubject);

        JLabel l2 = new JLabel("Duration:");
        l2.setForeground(text);
        l2.setFont(f);
        form.add(l2);

        txtDuration = new JTextField();
        form.add(txtDuration);

        JLabel l3 = new JLabel("Description:");
        l3.setForeground(text);
        l3.setFont(f);
        form.add(l3);

        txtDescription = new JTextArea();
        txtDescription.setLineWrap(true);
        form.add(new JScrollPane(txtDescription));

        JButton btnAdd = new JButton("Add Log");
        btnAdd.setBackground(primary);
        btnAdd.setForeground(Color.BLACK);
        btnAdd.setFont(f);
        btnAdd.setFocusPainted(false);
        form.add(btnAdd);

        main.add(form, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Date","Subject","Duration","Description"});

        table = new JTable(model);
        table.setBackground(card);
        table.setForeground(text);
        table.setRowHeight(28);

        table.getTableHeader().setBackground(primary);
        table.getTableHeader().setForeground(Color.BLACK);

        main.add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== BUTTONS =====
        JPanel bottom = new JPanel();
        bottom.setBackground(bg);

        JButton btnView = new JButton("View Logs");
        JButton btnExport = new JButton("Export CSV");

        btnView.setBackground(primary);
        btnExport.setBackground(primary);

        btnView.setForeground(Color.BLACK);
        btnExport.setForeground(Color.BLACK);

        bottom.add(btnView);
        bottom.add(btnExport);

        main.add(bottom, BorderLayout.SOUTH);

        add(main);

        // ===== ACTIONS =====
        btnAdd.addActionListener(e -> {
            try {
                tracker.InsertLog(
                        txtSubject.getText(),
                        Double.parseDouble(txtDuration.getText()),
                        txtDescription.getText()
                );
                JOptionPane.showMessageDialog(this, "Added!");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input");
            }
        });

        btnView.addActionListener(e -> {
            model.setRowCount(0);
            for(Study_Log s : tracker.Database)
            {
                model.addRow(new Object[]{s.getDate(), s.getSubject(), s.getDuration(), s.getDescription()});
            }
        });

        btnExport.addActionListener(e -> {
            tracker.Export_CSV();
            JOptionPane.showMessageDialog(this, "Exported!");
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

        if(choice == 2)
        {
            SwingUtilities.invokeLater(() -> new StudyTrackerGUI(stobj).setVisible(true));
        }
    }
}
