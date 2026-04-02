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

    // GUI insert
    public void InsertLog(String sub, double dur, String desc)
    {
        Study_Log studyobj = new Study_Log(LocalDate.now(), sub, dur, desc);
        Database.add(studyobj);
    }

    // CLI insert
    public void InsertLog()
    {
        Scanner sobj = new Scanner(System.in);

        System.out.println("---------------------------------------------");
        System.out.println("------Enter Valid Details of your study------");
        System.out.println("---------------------------------------------");

        System.out.println("Enter Subject:");
        String sub = sobj.nextLine();

        System.out.println("Enter Duration (hrs):");
        double dur = sobj.nextDouble();
        sobj.nextLine();

        System.out.println("Enter Description:");
        String desc = sobj.nextLine();

        InsertLog(sub, dur, desc);

        System.out.println("Study log saved successfully");
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
                fwobj.write(s.getDate()+","+s.getSubject()+","+s.getDuration()+","+s.getDescription()+"\n");
            }
            System.out.println("CSV Exported Successfully");
        }
        catch(Exception e)
        {
            System.out.println("Error in CSV");
        }
    }

    public void SummaryByDate()
    {
        TreeMap<LocalDate, Double> map = new TreeMap<>();

        for(Study_Log s : Database)
        {
            map.put(s.getDate(), map.getOrDefault(s.getDate(), 0.0) + s.getDuration());
        }

        for(LocalDate d : map.keySet())
        {
            System.out.println(d + " -> " + map.get(d) + " hrs");
        }
    }

    public void SummaryBySubject()
    {
        TreeMap<String, Double> map = new TreeMap<>();

        for(Study_Log s : Database)
        {
            map.put(s.getSubject(), map.getOrDefault(s.getSubject(), 0.0) + s.getDuration());
        }

        for(String sub : map.keySet())
        {
            System.out.println(sub + " -> " + map.get(sub) + " hrs");
        }
    }
}

// ================= GUI CLASS (UNCHANGED LOOK) =================
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

        setTitle("Study Tracker");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color bg = new Color(30, 32, 40);
        Color card = new Color(44, 47, 57);
        Color primary = new Color(0, 173, 181);
        Color text = Color.WHITE;

        getContentPane().setBackground(bg);

        JLabel title = new JLabel("Study Tracker Dashboard", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(text);
        add(title, BorderLayout.NORTH);

        JPanel main = new JPanel(new BorderLayout(15,15));
        main.setBackground(bg);

        JPanel form = new JPanel(new GridLayout(4,2,10,10));
        form.setBackground(card);

        JLabel l1 = new JLabel("Subject:");
        l1.setForeground(text);
        form.add(l1);

        txtSubject = new JTextField();
        form.add(txtSubject);

        JLabel l2 = new JLabel("Duration:");
        l2.setForeground(text);
        form.add(l2);

        txtDuration = new JTextField();
        form.add(txtDuration);

        JLabel l3 = new JLabel("Description:");
        l3.setForeground(text);
        form.add(l3);

        txtDescription = new JTextArea();
        form.add(new JScrollPane(txtDescription));

        JButton btnAdd = new JButton("Add Log");
        btnAdd.setBackground(primary);
        form.add(btnAdd);

        main.add(form, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Date","Subject","Duration","Description"});
        table = new JTable(model);

        main.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setBackground(bg);

        JButton btnView = new JButton("View Logs");
        JButton btnExport = new JButton("Export CSV");

        bottom.add(btnView);
        bottom.add(btnExport);

        main.add(bottom, BorderLayout.SOUTH);

        add(main);

        // Actions
        btnAdd.addActionListener(e -> {
            tracker.InsertLog(
                    txtSubject.getText(),
                    Double.parseDouble(txtDuration.getText()),
                    txtDescription.getText()
            );
            JOptionPane.showMessageDialog(this, "Added!");
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
        sobj.nextLine(); // fix input bug

        if(choice == 1)
        {
            int iCHOICE = 0;
            do
            {
                System.out.println("\n1 Insert | 2 View | 3 Export | 4 Date Summary | 5 Subject Summary | 6 Exit");
                iCHOICE = sobj.nextInt();
                sobj.nextLine();

                switch(iCHOICE)
                {
                    case 1: stobj.InsertLog(); break;
                    case 2: stobj.DisplayLog(); break;
                    case 3: stobj.Export_CSV(); break;
                    case 4: stobj.SummaryByDate(); break;
                    case 5: stobj.SummaryBySubject(); break;
                    case 6: System.out.println("Thank you!"); break;
                    default: System.out.println("Invalid choice");
                }

            } while(iCHOICE != 6);
        }
        else
        {
            SwingUtilities.invokeLater(() -> new StudyTrackerGUI(stobj).setVisible(true));
        }
    }
}
