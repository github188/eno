package com.energicube.eno.asset.service;


import com.energicube.eno.asset.model.IdGen;

/**
 * ID生成服务
 */
public interface IdGenService {

    /**
     * 获取对象值
     *
     * @param id 对象ID
     */
    public IdGen findOne(String id);

    /**
     * 生成新的ID
     *
     * @param id 对象ID
     */
    public String generateNewId(String id);

    /**
     * 生成新的ID
     *
     * @param id        对象ID
     * @param initValue 初始值
     */
    public String generateNewId(String id, long initValue);

    /**
     * 生成新的ID
     *
     * @param key        对象ID
     * @param prefix     前缀
     * @param seedlength 长度
     * @param separator  分隔符
     * @return 新的ID
     */
    public String generateNewId(String key, String prefix, int seedlength, long seedstart, String separator);


    /**
     * 生成新的ID
     *
     * @param key        对象ID
     * @param prefix     前缀
     * @param seedlength 长度
     * @param separator  分隔符
     * @param update     是否更新
     * @return 新的ID
     */
    public String generateAndUpdateNewId(String key, String prefix, int seedlength, long seedstart, String separator, boolean update);


    /**
     * 更新ID.
     *
     * @param id 对象ID
     */
    public long updateNewId(String id);


}
