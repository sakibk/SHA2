package data;

import java.sql.Date;

/**
 * Created by HP on 1/9/2017.
 */

public class PropertyList {

    public String Id ;
    public String OccupancyDate ;
    public String Description ;
    public String Title ;
    public String Price ;
    public String location ;

    public void setId(String x){
        Id=x;
    }
    public void setOccupancyDate(String x){
        OccupancyDate=x;
    }
    public void setDescription(String x){
        Description=x;
    }
    public void setTitle(String x){
        Title=x;
    }
    public void setPrice(String x){
        Price=x;
    }
    public void setLocation(String x){
        location=x;
    }

    public String getId(){
        return Id;
    }

    public String getOccupancyDate(){
        return OccupancyDate;
    }

    public String getDescription(){
        return Description;
    }

    public String getTitle(){
        return Title;
    }
    public String getPrice(){
        return Price;
    }

    public String getLocation(){
        return location;
    }






}
