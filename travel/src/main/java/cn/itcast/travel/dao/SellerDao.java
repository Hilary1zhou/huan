package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    /**
     * ����sid��ѯ������Ϣ
     *
     * @param sid
     * @return
     */
    public Seller findBySid (int sid);
}
