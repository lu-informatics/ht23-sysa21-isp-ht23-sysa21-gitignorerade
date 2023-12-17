package lu.ics.se.models;
import java.util.ArrayList;

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
    
            for (ServiceEvent event : workshop.getServiceEvent()) {
                totalCost += event.getEventCost();
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
            return workshopsAvailable;
        }

        public Workshop getWorkshopByName(String workshopName) {
            for (Workshop workshop : workshopsAvailable) {
                if (workshop.getWorkshopName().equals(workshopName)) {
                    return workshop;
                }
            }
            return null; // Handle the case when no workshop is found
        }

        
    }
    

