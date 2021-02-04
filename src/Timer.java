public class Timer {
    private int raw = 0;
    private int sec = 0;
    private int min = 0;
    private int hr = 0;

    private final int tickMark;
    private boolean matchesTick = false;

    public Timer(int tickMark){
        this.tickMark = tickMark;
    }

    public void increment(){
        matchesTick = (++raw % tickMark) == 0;

        if ((++sec % 60) == 0){
            sec = 0;
            if ((++min % 60) == 0){
                min = 0;
                hr++;
            }
        }

    }

    public boolean matchesTick(){
        return matchesTick;
    }

    public int getRaw(){
        return raw;
    }

    public int getTimeToNextTick(){
        return tickMark - (raw % tickMark);
    }

    public int getTotalTicks(){
        return raw / tickMark;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(hr > 0) sb.append(hr).append("h ");
        if(min > 0) sb.append(min).append("m ");
        sb.append(sec).append("s");
        return sb.toString();
    }
}
