package monitor;

import java.util.Vector;

public class Source {
    Vector<Monitor> monitors = new Vector<>();
    public Source(Monitor monitor) {
        monitors.add(monitor);
    }

    public void addMonitor(Monitor monitor) {
        monitors.add(monitor);
    }

    private void notifyMonitor(Event event) {
        for (Monitor monitor : monitors) {
            monitor.listenRun(event);
        }
    }

    public String walk() {
        Event event = new Event();
        event.setAction("walk");
        System.out.println("walk");
        return event.getAction();
    }

    public String run() {
        Event event = new Event();
        event.setAction("run");
        notifyMonitor(event);
        System.out.println("run");
        return event.getAction();
    }

    public static void main(String[] args) {
        Monitor monitor = new Monitor() {
            @Override
            public String listenRun(Event event) {
                System.out.println("the event is running");
                return "the event is running";
            }
        };

        Source source = new Source(monitor);

        source.walk();
        source.run();
    }
}
