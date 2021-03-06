package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgImpl();
    private SellerDao sellerdao = new SellerDaoImpl();

    @Override
    public Route findOne(String rid) {
        // 1.根据id去route表中查询route对象
        Route route = routeDao.findOne(Integer.parseInt(rid));
        // 2.1根据route的id 查询图片集合信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        // 2.2将集合设置到route对象
        route.setRouteImgList(routeImgList);
        Seller seller = sellerdao.findBySid(route.getSid());
        route.setSeller(seller);
        return route;
    }

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        // 封装bean对象
        PageBean<Route> pb = new PageBean<Route>();
        // 设置当前页码 currentPage
        pb.setCurrentPage(currentPage);
        // 设置每页显示条数 pageSize
        pb.setPageSize(pageSize);
        // 设置总记录数  totalCount
        int totalCount = routeDao.findTotalCount(cid, rname);
        pb.setTotalCount(totalCount);
        // 设置当前页所显示的数据集合
        int start = (currentPage - 1) * pageSize; // 开始的记录数
        List<Route> list = routeDao.findByPage(cid, start, pageSize, rname);
        pb.setList(list);
        // 设置总页数=总记录数/每页显示条数  不能整除则+1
        int totalPage =
                totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }
}
