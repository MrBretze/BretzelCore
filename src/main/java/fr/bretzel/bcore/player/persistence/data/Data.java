package fr.bretzel.bcore.player.persistence.data;

import com.google.gson.JsonElement;

public class Data
{
    private final String key;
    private final Object data;

    protected Data(String key, Object data)
    {
        this.key = key;
        this.data = data;
    }

    public String getKey()
    {
        return key;
    }

    public Object getData()
    {
        return data;
    }

    public Number getAsNumber()
    {
        return getData() instanceof Number ? (Number) getData() : null;
    }

    @Override
    public Object clone()
    {
        try
        {
            return super.clone();
        } catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
