package fr.bretzel.bcore.player.persistence;

import fr.bretzel.bcore.player.BPlayer;
import fr.bretzel.bcore.player.persistence.data.ArrayData;
import fr.bretzel.bcore.player.persistence.data.Data;
import fr.bretzel.bcore.player.persistence.data.ObjectData;

import java.util.ArrayList;

public class PersistenceData
{
    private final ArrayList<Data> dataList = new ArrayList<>();
    private BPlayer owner;
    private final String key;

    public PersistenceData(String entryKey)
    {
        this.key = entryKey;
    }

    public String getKey()
    {
        return key;
    }

    public void addData(Data data)
    {
        dataList.add(data);
    }

    public void addData(String key, Object data)
    {
        dataList.add(findCorrectData(key, data));
    }

    protected Data findCorrectData(String key, Object data)
    {
        if (data instanceof Iterable)
            return new ArrayData<>(key, (Iterable<?>) data);

        return new ObjectData(key, data);
    }


    /**
     * @param key the data key
     * @return true  if the key is present
     */
    public boolean hasData(String key)
    {
        return dataList.stream().anyMatch(filterData -> filterData.getKey().equals(key));
    }

    public void setOwner(BPlayer owner)
    {
        this.owner = owner;
    }

    public BPlayer getOwner()
    {
        return owner;
    }

    @Override
    protected Object clone()
    {
        PersistenceData persistenceData = new PersistenceData(getKey());

        for (Data data : dataList)
            persistenceData.dataList.add((Data) data.clone());

        persistenceData.setOwner(getOwner());

        return persistenceData;
    }
}
