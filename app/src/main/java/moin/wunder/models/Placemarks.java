package moin.wunder.models;

public class Placemarks
{
    private String interior;

    private String engineType;

    private String exterior;

    private String address;

    private String vin;

    private String name;

    private String fuel;

    private String[] coordinates;

    public String getInterior ()
    {
        return interior;
    }

    public void setInterior (String interior)
    {
        this.interior = interior;
    }

    public String getEngineType ()
    {
        return engineType;
    }

    public void setEngineType (String engineType)
    {
        this.engineType = engineType;
    }

    public String getExterior ()
    {
        return exterior;
    }

    public void setExterior (String exterior)
    {
        this.exterior = exterior;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getVin ()
    {
        return vin;
    }

    public void setVin (String vin)
    {
        this.vin = vin;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getFuel ()
    {
        return fuel;
    }

    public void setFuel (String fuel)
    {
        this.fuel = fuel;
    }

    public String[] getCoordinates ()
    {
        return coordinates;
    }

    public void setCoordinates (String[] coordinates)
    {
        this.coordinates = coordinates;
    }

}


