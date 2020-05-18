package com.nwf.sports.mvp.model;

import com.nwf.sports.utils.Strings;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Nereus on 2017/6/16.
 */
public class ProvinceCity
{
    public Long id;
    public String[] provinces;
    public  List<List<String>> cities;

    public ProvinceCity(Long id, String[] provinces,  List<List<String>> cities)
    {
        this.id = id;
        this.provinces = provinces;
        this.cities = cities;
    }

    public ProvinceCity()
    {
    }

    @Override
    public String toString()
    {
        return "ProvinceCity{" +
                "id=" + id +
                ", provinces=" + Arrays.toString(provinces) +
                ", cities=" + Strings.toString(cities) +
                '}';
    }
}
