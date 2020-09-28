package fr.bretzel.bcore.fakeentity;

import fr.bretzel.bcore.nms.entity.MCArmorStand;
import fr.bretzel.bcore.nms.entity.MCEntity;
import org.bukkit.Location;

public class FakeArmorStand extends FakeEntity
{
    private final MCArmorStand entityArmorStand;

    public FakeArmorStand(Location location)
    {
        super(location);
        entityArmorStand = new MCArmorStand(location);
    }

    @Override
    protected FakeEntity clone()
    {
        return new FakeArmorStand(getLocation());
    }

    @Override
    public MCEntity getEntity()
    {
        return entityArmorStand;
    }
}
