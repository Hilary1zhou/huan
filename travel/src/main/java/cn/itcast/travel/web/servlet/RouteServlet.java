package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * @author huan.zhou
 */
@WebServlet ("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService service = new RouteServiceImpl();

    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /**
         * 获取参数
         * currentPage 当前页码
         * pageSize 每页显示的条数
         * cid 分页id
         * rname 线路名称
         */
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");
        //设置编码，防止乱码
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        //处理参数，将参数类型转换为int
        int cid = 0;
        if (cidStr != null && cidStr.length() > 0) {
            cid = Integer.parseInt(cidStr);
        }
        //当前页码，如果值不传递，默认为1
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        //每页显示的条数，如果值不传递，默认为5
        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }
        PageBean<Route> pb = service.pageQuery(cid, currentPage, pageSize, rname);
        //将传入的对象序列化json，写回客户端
        writeValue(pb, response);
    }

    /**
     * 根据rid查询旅游线路的详细信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收id
        String rid = request.getParameter("rid");
        //2.调用service查询
        Route route = service.findOne(rid);
        //3.json返回
        writeValue(route, response);
    }
}
