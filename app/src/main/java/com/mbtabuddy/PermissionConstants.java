package com.mbtabuddy;

public enum PermissionConstants
{
    LOCATION_TrackerFragment(121);
    private final int value;

    PermissionConstants(int i) {
        this.value = i;
    }

    public int getValue()
    {
        return value;
    }

    public static PermissionConstants getEnum(int i)
    {
        for(PermissionConstants theEnum : PermissionConstants.values())
        {
            if(theEnum.getValue() == i)
            {
                return theEnum;
            }
        }

        return null;
    }
}
