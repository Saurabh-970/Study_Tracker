import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.io.*;

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

    public LocalDate getDate()
    {
        return this.Date;
    }

    public String getSubject()
    {
        return this.Subject;
    }

    public double getDuration()
    {
        return this.Duration;
    }

    public String getDescription()
    {
        return this.Description;
    }

    @Override
    public String toString()
    {
        return Date+" | "+Subject+" | "+Duration+" | "+Description;
    }
    
}

class Study_Tracker
{
    public ArrayList <Study_Log> Database = new ArrayList<Study_Log>();


    public void InsertLog()
    {
        Scanner sobj = new Scanner(System.in);

        System.out.println("---------------------------------------------");
        System.out.println("------Enter Valid Details of your study------");
        System.out.println("---------------------------------------------");

        LocalDate Dateobj = LocalDate.now();

        System.out.println("Please enter the name of subject like C/C++/java/python : ");
        String sub = sobj.nextLine();

        System.out.println("Enter the time period of your study in hrs");
        double dur = sobj.nextDouble();

        sobj.nextLine();

        System.out.println("Enter the description of what you are studying ");
        String desc = sobj.nextLine();

        Study_Log studyobj = new Study_Log(Dateobj , sub , dur , desc);
        Database.add(studyobj);

        System.out.println("Study log saved successfully");
        System.out.println("---------------------------------------------");
    }


    public void DisplayLog()
    {
        System.out.println("---------------------------------------------");
        System.out.println("---Log Report of Study Tracker Application---");
        System.out.println("---------------------------------------------");

        if(Database.isEmpty())
        {
            System.out.println("---------------------------------------------");
            System.out.println("--------- -----Nothing to Display------------");
            System.out.println("---------------------------------------------");
            return;
        }


        for(Study_Log s: Database)
        {
            System.out.println(s);
        }
        System.out.println("---------------------------------------------");

    }

    public void Export_CSV()
    {
        
        if(Database.isEmpty())
        {
            System.out.println("---------------------------------------------");
            System.out.println("--------- -----Nothing to Display------------");
            System.out.println("---------------------------------------------");
            return;
        }

        String FileName =  "StudyTracker.csv";

        try(FileWriter fwobj = new FileWriter(FileName))
        {
            fwobj.write("Data , Subject , Duration ,Description\n");

            for(Study_Log s : Database)
            {
                fwobj.write(s.getDate()+","+
                            s.getSubject().replace(","," ")+","+
                            s.getDuration()+","+
                            s.getDescription().replace(","," ")+"\n");

            }
            System.out.println("Data gets exported in csv : "+FileName);
        }
        catch(Exception eobj)
        {
            System.out.println("Exception occured in CSV handling");
        }


    }

    public void SummaryByDate()
    {
         System.out.println("---------------------------------------------");
         if(Database.isEmpty())
         {
            System.out.println("---Nothing to Display as database is empty---");
            System.out.println("---------------------------------------------");

            return;
         }

        System.out.println("----Summary of Dates by study tracker-----");
        System.out.println("---------------------------------------------");
        TreeMap <LocalDate , Double>tobj = new TreeMap<LocalDate, Double>();

        LocalDate lobj = null;
        Double d = 0.0, old = 0.0;
        for(Study_Log sobj : Database)
        {
            lobj = sobj.getDate();
            d = sobj.getDuration();

            if(tobj.containsKey(lobj))
            {
                old = tobj.get(lobj);
                tobj.put(lobj,d+old); // old value + new updated study of hrs
            }
            else
            {
                tobj.put(lobj,d);
            }

        }

        //Display the details as per date
        for(LocalDate ld : tobj.keySet())
        {
            System.out.println("Date : "+ld+"Total study duration : "+tobj.get(ld));
        }
         System.out.println("---------------------------------------------");

    }

    

    public void SummaryBySubject()
    {
         System.out.println("---------------------------------------------");
         if(Database.isEmpty())
         {
            System.out.println("---Nothing to Display as database is empty---");
            System.out.println("---------------------------------------------");

            return;
         }

        System.out.println("----Summary of Subjects by study tracker-----");
        System.out.println("---------------------------------------------");
        TreeMap <String , Double>tobj = new TreeMap<String, Double>();

        String s = null;
        Double d = 0.0, old = 0.0;
        for(Study_Log sobj : Database)
        {
            s = sobj.getSubject();
            d = sobj.getDuration();

            if(tobj.containsKey(s))
            {
                old = tobj.get(s);
                tobj.put(s,d+old); // old value + new updated study of hrs
            }
            else
            {
                tobj.put(s,d);
            }

        }

        //Display the details as per subject 
        for(String str : tobj.keySet())
        {
            System.out.println("Subject : "+str+" Total study duration : "+tobj.get(str));
        }
         System.out.println("---------------------------------------------");

    }
    
}

class StudyTrackerApplicationCUI
{
    public static void main(String A[]) 
    {
        Scanner sobj = new Scanner(System.in);
        Study_Tracker stobj = new Study_Tracker();
        System.out.println("----------------------------------------------------------");
        System.out.println("-----------Welcome to Study Tracker Application-----------");
        System.out.println("----------------------------------------------------------");

        int iCHOICE = 0;
        do
        {
            System.out.println("Please select appropriate option : ");
            System.out.println(" 1 : Insert new study log");
            System.out.println(" 2 : View all study logs");
            System.out.println(" 3 : Export study log to CSV file");
            System.out.println(" 4 : Summary of study log by date");
            System.out.println(" 5 : Summary of study log by subject");
            System.out.println(" 6 : Exit the application");


            iCHOICE = sobj.nextInt();

            switch(iCHOICE)
            {
                //Insert new study log
                case 1 : 
                    stobj.InsertLog();
                    break;

                //View all study logs
                case 2 :
                    stobj.DisplayLog();
                    break;
                
                //Export study log to CSV file
                case 3 :
                    stobj.Export_CSV();
                    break;

                //Summary of study log by date
                case 4 :
                    stobj.SummaryByDate();
                    break;

                //Summary of study log by subject
                case 5 :
                    stobj.SummaryBySubject();
                    break;

                //exit the application
                case 6 :
                    System.out.println("----------------------------------------------------------");
                    System.out.println("-------Thankyou For using Study Tracker Application-------");
                    System.out.println("----------------------------------------------------------");
                    break;
                    
                default:
                    System.out.println("Please enter valid option");
                    break;

            }
            

        }while(iCHOICE != 6 ); //end of do while
        


    }//end of main
}//end of starter class
