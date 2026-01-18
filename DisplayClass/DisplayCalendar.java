

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.List;

public class DisplayCalendar {
    private int year;
    private int weekNumber;
    private int monthNumber;
    private EventManager manager;

    public DisplayCalendar(int year, int weekNumber, int monthNumber, EventManager manager) {
        //checking invalid inputs
        if(year < 0 || weekNumber < 0 || weekNumber > 52 || monthNumber < 0 || monthNumber > 12){
            throw new IllegalArgumentException("Invalid input of year, week number or month number.");
        }else{
        this.year = year;
        this.weekNumber = weekNumber;
        this.monthNumber = monthNumber;
        this.manager = manager;
        }
    }

    public void displayWeek(int weekNumber) {
        // WeekFields: ISO standard (week starts on Monday by default)
        WeekFields weekFields = WeekFields.ISO;
        LocalDate date = LocalDate.of(year,1,1);
        
        while(date.get(weekFields.weekOfYear()) != weekNumber){
            date = date.plusDays(1);
        }
        date = date.with(weekFields.dayOfWeek(),1);
        System.out.println("Week " + weekNumber + " of " + year);
        
        for(int i = 0; i < 7; i++){
            System.out.printf("%-10s %02d/%02d/%d%n",date.getDayOfWeek(),
                    date.getDayOfMonth(),
                    date.getMonthValue(), 
                    date.getYear());
            
            List<Event> events = manager.getEventsOnDate(date);
            
            if(events.isEmpty()){
                System.out.println(" No Event ");
            }else{
                for(Event e : events){
                    System.out.println(" " + e.getStartDateTime().toLocalTime() +
                                       " - " + e.getTitle());
                }
            }
            
            
            date = date.plusDays(1);
        }
    }

    public void displayMonthly(int monthNumber,int year){
        //get month in letter
        Month month = Month.of(monthNumber);
        //first day of month
        LocalDate firstDay = LocalDate.of(year,month,1);
        //total day in a month
        int totalDay = firstDay.lengthOfMonth();
        //day of week
        int startDay = firstDay.getDayOfWeek().getValue();
        int index = startDay - 1;
        
        System.out.println(month + " " + year);
        System.out.println("Mon Tue Wed Thu Fri Sat Sun");
        for(int i = 0; i < index; i++){
            System.out.print("    ");
        }
        for(int day = 1; day <= totalDay; day++){
            LocalDate current = LocalDate.of(year, monthNumber, day);
            
        List<Event> events = manager.getEventsOnDate(current);
        boolean hasEvent = !events.isEmpty();
            
            if(hasEvent){
                System.out.printf("%2d* ", day);
            }else{
                System.out.printf("%2d  ", day);
            }
            
            if((day+index)%7 == 0){
                System.out.println();
            }
        }
        System.out.println("\nEvents:");

        for (int day = 1; day <= totalDay; day++) {
            LocalDate current = LocalDate.of(year, monthNumber, day);
            List<Event> eventsToday = manager.getEventsOnDate(current);

            for (Event e : eventsToday) {
                 System.out.println(current + " "
                                    + e.getStartDateTime().toLocalTime()
                                    + " - " + e.getTitle());
    }
}


}
}
