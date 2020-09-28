package fr.bretzel.bcore.player.persistence.data;

public class ObjectData extends Data
{
    private final Object data;

    public ObjectData(String key, Object data)
    {
        super(key);
        this.data = data;
    }

    @Override
    public Object getData()
    {
        return data;
    }
}
