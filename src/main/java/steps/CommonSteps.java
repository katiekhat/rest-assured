package steps;

public class CommonSteps <T,TD>{
    protected TD data;
    public T setData(TD data){
        this.data=data;
        return (T) this;
    }
}
