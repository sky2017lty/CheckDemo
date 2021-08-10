package com.poshing.checkdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.poshing.checkdemo.dao.ChecklogDao;
import com.poshing.checkdemo.dao.FeedingcheckDao;
import com.poshing.checkdemo.entity.Checklog;
import com.poshing.checkdemo.entity.Feedingcheck;
import com.poshing.checkdemo.service.CheckServices;
import com.poshing.checkdemo.utils.JsonUtils;
import com.poshing.checkdemo.utils.UUIDUtils;
import com.poshing.checkdemo.utils.Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author litianyi
 */
@Service
public class CheckServicesImpl implements CheckServices {

    @Resource
    private FeedingcheckDao feedingcheckDao;

    @Resource
    private ChecklogDao checklogDao;

    @Override
    public String checkData(HttpServletRequest request) {
        List<Feedingcheck> checkList = feedingcheckDao.selectList(new QueryWrapper<Feedingcheck>());
        return JsonUtils.getInstance().formatLayerJson(0, "success", checkList.size(), JSON.toJSONString(checkList));
    }

    @Override
    public String addFeedingCheck(HttpServletRequest request) {
        String feedingMes = request.getParameter("feeding_MES");
        String feedingNo = request.getParameter("feeding_no");
        String feedingMachine = request.getParameter("feeding_machine");
        String username = Utils.getSession(request, "username");
        boolean mes = Utils.isNull(feedingMes);
        boolean no = Utils.isNull(feedingNo);
        boolean machine = Utils.isNull(feedingMachine);
        if (mes || no || machine) {
            return JsonUtils.getInstance().formatLayerJson(200, "数据错误，请重新核对");
        }
        Feedingcheck selectOne = feedingcheckDao.selectOne(new QueryWrapper<Feedingcheck>()
                .eq("feeding_MES", feedingMes));
        if (selectOne == null) {
            Feedingcheck one = new Feedingcheck();
            one.setUuid(UUIDUtils.getUuid());
            one.setFeedingMes(feedingMes);
            one.setFeedingNo(feedingNo);
            one.setFeedingMachine(feedingMachine);
            one.setUsername(username);
            one.setDate(Utils.getDate());
            one.setTime(Utils.getTime());
            int flag = feedingcheckDao.insert(one);
            return Utils.returnJson(flag);
        } else {
            return JsonUtils.getInstance().formatLayerJson(200, "MES已进站");
        }

    }

    @Override
    public String cutting(HttpServletRequest request) {
        String cuttingMes = request.getParameter("cutting_MES");
        String cuttingNo = request.getParameter("cutting_no");
        String cuttingMachine = request.getParameter("cutting_machine");
        String username = Utils.getSession(request, "username");
        boolean mes = Utils.isNull(cuttingMes);
        boolean no = Utils.isNull(cuttingNo);
        boolean machine = Utils.isNull(cuttingMachine);
        if (mes || no || machine) {
            return JsonUtils.getInstance().formatLayerJson(200, "数据错误，请重新核对");
        }
        Feedingcheck selectOne = feedingcheckDao.selectOne(new QueryWrapper<Feedingcheck>()
                .eq("feeding_MES", cuttingMes));
        if (selectOne == null) {
            return JsonUtils.getInstance().formatLayerJson(200, "找不到MES");
        } else {
            return insertLog(selectOne, cuttingMes, cuttingNo, cuttingMachine, username);
        }
    }

    @Override
    public String getAllLog(HttpServletRequest request) {
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        List<Checklog> checklogList;
        if (Utils.isNull(start) && Utils.isNull(end)) {
            checklogList = checklogDao.selectList(new QueryWrapper<Checklog>());
        } else {
            checklogList = checklogDao.selectList(new QueryWrapper<Checklog>()
                    .ge("cutting_date", start)
                    .le("cutting_date", end));
        }
        return JsonUtils.getInstance().formatLayerJson(0, "success", checklogList.size(), JSON.toJSONString(checklogList));
    }

    private String insertLog(Feedingcheck feedingcheck, String cuttingMes, String cuttingNo, String cuttingMachine, String cuttingUsername) {
        Checklog checklog = new Checklog();
        checklog.setUuid(UUIDUtils.getUuid());
        checklog.setFeedingUsername(feedingcheck.getUsername());
        checklog.setFeedingDate(feedingcheck.getDate());
        checklog.setFeedingTime(feedingcheck.getTime());
        checklog.setFeedingMes(feedingcheck.getFeedingMes());
        checklog.setFeedingNo(feedingcheck.getFeedingNo());
        checklog.setFeedingMachine(feedingcheck.getFeedingMachine());
        checklog.setCuttingUsername(cuttingUsername);
        checklog.setCuttingDate(Utils.getDate());
        checklog.setCuttingTime(Utils.getTime());
        checklog.setCuttingMes(cuttingMes);
        checklog.setCuttingNo(cuttingNo);
        checklog.setCuttingMachine(cuttingMachine);
        int flag = 0;
        int flagFeeding = 0;
        if (feedingcheck.getFeedingMes().equals(cuttingMes) && feedingcheck.getFeedingNo().equals(cuttingNo) && feedingcheck.getFeedingMachine().equals(cuttingMachine)) {
            checklog.setResult("正确");
            flagFeeding = feedingcheckDao.deleteById(feedingcheck);
        } else {
            checklog.setResult("错误");
        }
        int flagCutting = checklogDao.insert(checklog);
        if (flagFeeding == 1 && flagCutting == 1) {
            flag = 1;
        }
        return Utils.returnJson(flag);
    }
}
