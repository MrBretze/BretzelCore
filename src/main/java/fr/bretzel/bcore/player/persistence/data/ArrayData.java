package fr.bretzel.bcore.player.persistence.data;

public class ArrayData<T> extends Data
{
    private final Iterable<T> array;

    public ArrayData(String key, Iterable<T> array)
    {
        super(key);
        this.array = array;
    }

    @Override
    public Iterable<T> getData()
    {
        return array;
    }
}
