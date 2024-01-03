package lu.ics.se.models;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Callback;

    public class WorkshopList {
        private ArrayList<Workshop> workshopsAvailable;
        
        public WorkshopList() {
            workshopsAvailable = new ArrayList<Workshop>();
        }

        public Workshop getMostExpensiveWorkshop() {
            if (workshopsAvailable.isEmpty()) {
                // Handle the case where there are no workshops available
                return null;
            }
        
            Workshop mostExpensiveWorkshop = null;
            double maxCost = Double.MIN_VALUE;
        
            for (Workshop workshop : workshopsAvailable) {
                double totalCost = calculateTotalCostForWorkshop(workshop);
                if (totalCost > maxCost) {
                    maxCost = totalCost;
                    mostExpensiveWorkshop = workshop;
                }
            }
        
            return mostExpensiveWorkshop;
        }
        
        public double calculateTotalCostForWorkshop(Workshop workshop) {
            double totalCost = 0;
        
            if(workshop.getServiceEvent() != null) {
                for (ServiceEvent event : workshop.getServiceEvent()) {
                    totalCost += event.getEventCost();
                }
            }
        
            return totalCost;
        }
    
    
        public void addWorkshop(Workshop workshop) {
            workshopsAvailable.add(workshop);
        }
    
        public void removeWorkshop(Workshop workshop) {
            workshopsAvailable.remove(workshop);
        }

        public ArrayList<Workshop> getAllWorkshops() {
            return new ArrayList<>(workshopsAvailable);
        }
        

        public Workshop getWorkshopByName(String workshopName) {
            for (Workshop workshop : workshopsAvailable) {
                if (workshop.getWorkshopName().equals(workshopName)) {
                    return workshop;
                }
            }
            return null; // Handle the case when no workshop is found
        }
        public List<String> getAllWorkshopNames() {
        List<String> names = new ArrayList<>();
        for (Workshop workshop : workshopsAvailable) {
        names.add(workshop.getWorkshopName());
        }
        return names;
}

        public void clearWorkshopList() {
            workshopsAvailable.clear();
        }

        
    }
    

