import java.util.*;

class Process {
    String name;
    int at;
    int bt;
    int pri;

    public Process(String name, int at, int bt, int pri) {
        this.name = name;
        this.at = at;
        this.bt = bt;
        this.pri = pri;
    }
}
class OccurrenceInfo {
    int firstOccurrence;
    int lastOccurrence;

    public OccurrenceInfo(int firstOccurrence, int lastOccurrence) {
        this.firstOccurrence = firstOccurrence;
        this.lastOccurrence = lastOccurrence;
    }
}
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Process> processes = new ArrayList<>();
//        System.out.print("Enter the number of processes: ");
//        int numProcesses = scanner.nextInt();
//        for (int i = 0; i < numProcesses; i++) {
//            System.out.println("Enter details for process " + (i + 1) + ":");
//            System.out.print("Name: ");
//            String name = scanner.next();
//            System.out.print("Arrival Time: ");
//            int arrivalTime = scanner.nextInt();
//            System.out.print("Burst Time: ");
//            int burstTime = scanner.nextInt();
//            System.out.print("Priority: ");
//            int priority = scanner.nextInt();
//            processes.add(new Process(name, arrivalTime, burstTime, priority));
//        }
        processes.add(new Process("P2", 1, 4, 2));
        processes.add(new Process("P4", 3, 3, 3));
        processes.add(new Process("P3", 2, 2, 1));
        processes.add(new Process("P1", 0, 6, 4));
//        processes.add(new Process("P5", 3, 2, 2));
        for (Process process : processes) {
            System.out.println("Name: " + process.name + ", Arrival Time: " + process.at + ", Burst Time: " + process.bt + ", Priority: " + process.pri);
        }
        System.out.println("Choose scheduling algorithm:");
        System.out.println("1. First Come First Serve (FCFS)");
        System.out.println("2. Shortest Job First (SJF)");
        System.out.println("3. Priority");
        System.out.println("4. Shortest Job First (SJF)(Preemptive)");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                fcfs(atSort(processes));
                break;
            case 2:
                sjf(processes);
                break;
            case 3:
                priority(processes);
                break;
            case 4:
                sjn(atSort(processes));
                break;
            default:
                System.out.println("Invalid choice!");
        }
        scanner.close();
    }

    public static void fcfs(ArrayList<Process> processes) {

        int currentTime = 0;
        float totalWaitTime = 0;
        float totalTurnaroundTime = 0;
        for (Process process : processes) {
            if (process.at > currentTime) currentTime = process.at;
            int waitTime = currentTime - process.at;
            totalWaitTime += waitTime;
            int turnaroundTime = waitTime + process.bt;
            totalTurnaroundTime += turnaroundTime;
            currentTime += process.bt;
            System.out.println("Process " + process.name + " executed from time " + (currentTime - process.bt) + " to " + currentTime + ". Wait time: " + waitTime + ". Turnaround time: " + turnaroundTime);
        }
        float avgWaitTime = totalWaitTime / processes.size();
        float avgTurnaroundTime = totalTurnaroundTime / processes.size();
        processes.clear();
        System.out.println("Average waiting time: " + avgWaitTime);
        System.out.println("Average turnaround time: " + avgTurnaroundTime);
    }

    public static ArrayList<Process> atSort(ArrayList<Process> processes) {
        int n = processes.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (processes.get(j).at > processes.get(j + 1).at) {
                    Process temp = processes.get(j);
                    processes.set(j, processes.get(j + 1));
                    processes.set(j + 1, temp);
                }
            }
        }
        return processes;
    }

    public static void sjf(ArrayList<Process> processes) {
        int size = processes.size();
        Process process = null;
        int currTime = 0;
        float totalWaitTime = 0;
        float totalTurnaroundTime = 0;
        while (!processes.isEmpty()) {
            int minBt = Integer.MAX_VALUE;
            boolean found = false;
            for (Process temp : processes) {
                if (temp.at <= currTime) {
                    if (temp.bt < minBt) {
                        minBt = temp.bt;
                        process = temp;
                        found = true;
                    }
                }
            }
            if (!found) {
                currTime++;
                continue;
            }
            processes.remove(process);
            int waitTime = currTime - process.at;
            totalWaitTime += waitTime;
            int turnaroundTime = waitTime + process.bt;
            totalTurnaroundTime += turnaroundTime;
            currTime += process.bt;
            System.out.println("Process " + process.name + " executed from time " + (currTime - process.bt) + " to " + currTime + ". Wait time: " + waitTime + ". Turnaround time: " + turnaroundTime);
        }
        float avgWaitTime = totalWaitTime / size;
        float avgTurnaroundTime = totalTurnaroundTime / size;
        System.out.println("Average waiting time: " + avgWaitTime);
        System.out.println("Average turnaround time: " + avgTurnaroundTime);
    }

    public static void priority(ArrayList<Process> processes) {
        int size = processes.size();
        Process process = null;
        int currTime = 0;
        float totalWaitTime = 0;
        float totalTurnaroundTime = 0;
        while (!processes.isEmpty()) {
            int minPri = Integer.MAX_VALUE;
            boolean found = false;
            for (Process temp : processes) {
                if (temp.at <= currTime) {
                    if (temp.pri < minPri) {
                        minPri = temp.pri;
                        process = temp;
                        found = true;
                    }
                }
            }
            if (!found) {
                currTime++;
                continue;
            }
            processes.remove(process);
            int waitTime = currTime - process.at;
            totalWaitTime += waitTime;
            int turnaroundTime = waitTime + process.bt;
            totalTurnaroundTime += turnaroundTime;
            currTime += process.bt;
            System.out.println("Process " + process.name + " executed from time " + (currTime - process.bt) + " to " + currTime + ". Wait time: " + waitTime + ". Turnaround time: " + turnaroundTime);
        }
        float avgWaitTime = totalWaitTime / size;
        float avgTurnaroundTime = totalTurnaroundTime / size;
        System.out.println("Average waiting time: " + avgWaitTime);
        System.out.println("Average turnaround time: " + avgTurnaroundTime);
    }

    public static void sjn(ArrayList<Process> processes) {
        int size = processes.size();
        Process process = null;
        int currTime = 0;
        float totalWaitTime = 0;
        float totalTurnaroundTime = 0;
        List<Process> ganttChart = new ArrayList<>();
        while (!processes.isEmpty()) {
            int minBt = Integer.MAX_VALUE;
            for (Process temp : processes) {
                if (temp.at <= currTime) {
                    if (temp.bt < minBt) {
                        minBt = temp.bt;
                        process = temp;
                    }
                }
            }
            currTime++;
            ganttChart.add(process);
            process.bt -= 1;
            if (process.bt == 0) processes.remove(process);
        }
        Map<Process, OccurrenceInfo> occurrenceMap = new HashMap<>();
        for (int i = 0; i < ganttChart.size(); i++) {
            Process value = ganttChart.get(i);
            if (!occurrenceMap.containsKey(value)) {
                occurrenceMap.put(value, new OccurrenceInfo(i, i));
            } else {
                OccurrenceInfo info = occurrenceMap.get(value);
                info.lastOccurrence = i;
                occurrenceMap.put(value, info);
            }
        }
        for (Map.Entry<Process, OccurrenceInfo> entry : occurrenceMap.entrySet()) {
            Process element = entry.getKey();
            OccurrenceInfo info = entry.getValue();
            int waitTime = info.firstOccurrence - element.at;
            totalWaitTime += waitTime;
            int turnaroundTime = info.lastOccurrence - element.at +1;
            totalTurnaroundTime += turnaroundTime;
            System.out.println("Process " + element.name + " executed from time " + info.firstOccurrence + " to " + (info.lastOccurrence+1) + ". Wait time: " + waitTime + ". Turnaround time: " + turnaroundTime);
        }
        float avgWaitTime = totalWaitTime / size;
        float avgTurnaroundTime = totalTurnaroundTime / size;
        System.out.println("Average waiting time: " + avgWaitTime);
        System.out.println("Average turnaround time: " + avgTurnaroundTime);
    }
}