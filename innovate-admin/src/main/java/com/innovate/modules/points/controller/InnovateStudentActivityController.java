package com.innovate.modules.points.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import java.util.Map;

import com.innovate.common.utils.Constant;
import com.innovate.common.utils.OSSUtils;
import com.innovate.modules.points.utils.QRCodeUtil;
import com.innovate.modules.util.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.innovate.modules.points.entity.InnovateStudentActivityEntity;
import com.innovate.modules.points.service.InnovateStudentActivityService;
import com.innovate.common.utils.PageUtils;
import com.innovate.common.utils.R;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 *
 * @author Mikey
 * @email biaogejiushibiao@outlook.com
 * @date 2020-11-10 13:11:17
 */
@RestController
@RequestMapping("points/innovatestudentactivity")
public class InnovateStudentActivityController {
    @Autowired
    private InnovateStudentActivityService innovateStudentActivityService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("points:innovatestudentactivity:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = innovateStudentActivityService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("points:innovatestudentactivity:info")
    public R info(@PathVariable("id") Long id){
		InnovateStudentActivityEntity innovateStudentActivity = innovateStudentActivityService.selectById(id);

        return R.ok().put("innovateStudentActivity", innovateStudentActivity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("points:innovatestudentactivity:save")
    public R save(@RequestBody InnovateStudentActivityEntity innovateStudentActivity){
		innovateStudentActivityService.insert(innovateStudentActivity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("points:innovatestudentactivity:update")
    public R update(@RequestBody InnovateStudentActivityEntity innovateStudentActivity){
		innovateStudentActivityService.updateById(innovateStudentActivity);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("points:innovatestudentactivity:delete")
    public R delete(@RequestBody Long[] ids){
		innovateStudentActivityService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 文件下载
     */
    @PostMapping(value = "/download")
    @RequiresPermissions("points:innovatestudentactivity:list")
    public void downloadQRCodeImage(final HttpServletResponse response, final HttpServletRequest request) {
        String filePath = request.getParameter("filePath");
        FileUtils.download(response, filePath);
    }

    /**
     * 返回二维码
     * @param realPath    路径
     * @throws Exception
     */
    @RequestMapping(value = "/image/{realPath}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("realPath") String realPath) throws Exception {

        byte[] imageContent;
        imageContent = fileToByte(OSSUtils.downloadFileFromOSS(Constant.QR_CODE_PATH+realPath));

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

    /**
     * 转文件格式
     * @param img   照片文件
     * @throws Exception
     */
    private byte[] fileToByte(File img) throws Exception {
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BufferedImage bi;
            bi = ImageIO.read(img);
            ImageIO.write(bi, "png", baos);
            bytes = baos.toByteArray();
        } catch (Exception e) {
//            e.printStackTrace();
        } finally {
            // 关闭输出流
            baos.close();
        }
        return bytes;
    }
}
