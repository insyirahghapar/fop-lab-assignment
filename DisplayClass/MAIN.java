
import java.util.Scanner;

public class MAIN {
    public static void main(String[] args){

        Scanner input = new Scanner(System.in);

        System.out.println("---- Calendar & Schedular App ----");
        System.out.println("1. View Calendar by Week");
        System.out.println("2. View Calender by Month");
        System.out.println("3. Add Event");
        System.out.println("4. Search Event");
        System.out.println("Choose action(enter the number): ");
        int selection = input.nextInt();

        if(selection == 1){
            while(true){
                try{
                    System.out.print("Enter year: ");
                    int year = input.nextInt();
                    System.out.print("Enter week number (1 - 52): ");
                    int week = input.nextInt();     
                    DisplayCalendar displayCalendar = new DisplayCalendar(year,week,0);
                    displayCalendar.displayWeek(week);
                    break;
                }
                catch(IllegalArgumentException e){
                    System.out.println(e.getMessage() + " Please try again.");
                }
            }
        }
        else if(selection == 2){
            while(true){
                try{
                    System.out.print("Enter year: ");
                    int year = input.nextInt();
                    System.out.print("Enter month number (1 - 12): ");
                    int month = input.nextInt();     
                    DisplayCalendar displayCalendar = new DisplayCalendar(year,0,month);
                    displayCalendar.displayMonthly(month,year);
                    break;
                }
                catch(IllegalArgumentException e){
                    System.out.println(e.getMessage() + " Please try again.");
                }
            }
        }
    }
}