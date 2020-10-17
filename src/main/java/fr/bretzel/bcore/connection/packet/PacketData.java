package fr.bretzel.bcore.connection.packet;

import fr.bretzel.bcore.utils.reflection.NMSReflection;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacketData
{
    private final Object nms_packet;

    public PacketData(Object nms)
    {
        nms_packet = nms;
    }

    public Object getNMSPacket()
    {
        return nms_packet;
    }

    public Object getObject(int index)
    {
        try
        {
            return getAllFields().get(index).get(nms_packet);
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param index  index of the field
     * @param defaul the default return value if the field is null or incorrect
     * @return to the field value or if it null, to your default value
     */
    public float getFloat(int index, float defaul)
    {
        return (float) get(getFieldOfType(Float.TYPE).get(index), defaul);
    }

    /**
     * @param index index of the field
     * @return to the field value or if it null, to 0
     */
    public float getFloat(int index)
    {
        return getFloat(index, 0);
    }

    /**
     * @param index  index of the field
     * @param defaul the default return value if the field is null or incorrect
     * @return to the field value or if it null, to your default value
     */
    public double getDouble(int index, double defaul)
    {
        return (double) get(getFieldOfType(Double.TYPE).get(index), defaul);
    }

    /**
     * @param index index of the field
     * @return to the field value or if it null, to 0
     */
    public double getDouble(int index)
    {
        return getDouble(index, 0);
    }

    /**
     * @param index  index of the field
     * @param defaul the default return value if the field is null or incorrect
     * @return to the field value or if it null, to your default value
     */
    public int getInt(int index, int defaul)
    {
        return (int) get(getFieldOfType(Integer.TYPE).get(index), defaul);
    }

    /**
     * @param index index of the field
     * @return to the field value or if it null, to 0
     */
    public int getInt(int index)
    {
        return getInt(index, 0);
    }

    /**
     * @param field  the field of your choice
     * @param defaul the default return value if the field is null or incorrect
     * @return to the field value or if it null, to your default value
     */
    protected Object get(Field field, Object defaul)
    {
        if (field == null)
            return defaul;
        if (!field.isAccessible())
            field.setAccessible(true);
        try
        {
            return field.get(nms_packet);
        } catch (IllegalAccessException e)
        {
            e.fillInStackTrace();
            return defaul;
        }
    }

    /**
     * @param type the type of your field list
     * @return a list of field with the same type
     */
    protected List<Field> getFieldOfType(Type type)
    {
        List<Field> field_list = new ArrayList<>();

        for (Field field : getAllFields())
            if (field.getType() == type)
                field_list.add(field);

        return field_list;
    }

    protected List<Field> getAllFields()
    {
        return getAllField(nms_packet.getClass());
    }

    private List<Field> getAllField(Class<?> clazz)
    {
        ArrayList<Field> arrayList = new ArrayList<>();
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class && clazz.getSuperclass() != NMSReflection.CLASS_PACKET)
        {
            arrayList.addAll(getAllField(clazz.getSuperclass()));
        } else
        {
            arrayList.addAll(Arrays.asList(clazz.getFields()));
            arrayList.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }

        arrayList.forEach(field ->
        {
            if (!field.isAccessible())
                field.setAccessible(true);
        });

        return arrayList;
    }

    public String getPacketName()
    {
        return getNMSPacket().getClass().getSimpleName();
    }
}
