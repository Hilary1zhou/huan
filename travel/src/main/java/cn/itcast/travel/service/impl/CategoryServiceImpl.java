package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<Category> findAll() {
        /** 缓存优化 1.从redis中查询 2.判断查询的集合是否为空 3.如果为空从数据库查询，再将数据存入redis 4.如果不为空，从redis查询，将set数据存入list */
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        List<Category> cs = null;
        if (category == null || category.size() == 0) {
            System.out.println("从数据库查询。。。");
            cs = categoryDao.findAll();
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category", cs.get(i).getCid(), cs.get(i).getCname());
            }

        } else {
            System.out.println("从redis中查询");
            cs = new ArrayList<Category>();
            for (Tuple tuple : category) {
                Category categorys = new Category();
                categorys.setCname(tuple.getElement());
                categorys.setCid((int) tuple.getScore());
                cs.add(categorys);
            }
        }
        return cs;
    }
}
