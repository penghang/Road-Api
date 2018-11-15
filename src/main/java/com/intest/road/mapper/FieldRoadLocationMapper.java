package com.intest.road.mapper;

import com.intest.road.pojo.FieldRoadLocation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FieldRoadLocationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table X_FIELD_LOCATION
     *
     * @mbggenerated Wed Nov 14 16:42:03 CST 2018
     */
    int insert(FieldRoadLocation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table X_FIELD_LOCATION
     *
     * @mbggenerated Wed Nov 14 16:42:03 CST 2018
     */
    List<FieldRoadLocation> selectAll();

    /**
     * 删除所有
     * @return
     */
    int deleteAll();

    List<FieldRoadLocation> selectByPidAndType(@Param("pid") Short pId, @Param("coordinateType") Short coordinateType);
}