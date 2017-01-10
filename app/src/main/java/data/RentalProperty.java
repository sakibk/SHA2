package data;

import java.sql.Date;

/**
 * Created by HP on 1/9/2017.
 */

public class RentalProperty {

    public int Id;
    public boolean Active;
    public Date OccupancyDate;
    public String Description;
    public String Title;
    public String ImagePath;
    public float Price;
    public double RatingA;

    public int LandlordID;
    public String landlordFirstName;
    public String landlordLastName;
    public int landlordAge;
    public String landlordEmail;
    public String landlordGender;
    public String landlordAddress;
    public String landlordContactNumber;

    public int AccommodationTypeID;
    public String accomodationTypeName;

    public int LeaseTypeID;
    public String leaseTypeName;

    public int PropertyPhotoID ;
    public byte[] One ;
    public byte[] Two ;
    public byte[] Three ;
    public byte[] Four ;
    public byte[] Five ;

    public int LocationID ;
    public String locationTitle ;
    public double locationLong ;
    public double locationLat ;
    public String locationAddress ;
}
