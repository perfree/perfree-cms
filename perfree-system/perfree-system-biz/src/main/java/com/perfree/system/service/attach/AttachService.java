package com.perfree.system.service.attach;

import com.perfree.commons.common.PageResult;
import com.perfree.system.model.Attach;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfree.system.vo.attach.AttachPageReqVO;
import com.perfree.system.vo.attach.AttachUploadVO;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
public interface AttachService extends IService<Attach> {

    /**
     * 附件分页查询
     * @param pageVO pageVO
     * @return PageResult<Attach>
     */
    PageResult<Attach> attachPage(AttachPageReqVO pageVO);

    /**
     * 附件创建
     * @param attach attach
     * @return Attach
     */
    Attach create(AttachUploadVO attach);

    /**
     * 附件删除
     * @param id id
     * @return Boolean
     */
    Boolean del(Integer id);

    /**
     * 附件获取文件内容
     * @param configId configId
     * @param path path
     * @return byte[]
     * @throws IOException IOException
     */
    byte[] getFileContent(Integer configId, String path);

    /**
     * 获取所有附件分组
     * @return List<AttachGroupRespVO>
     */
    List<Attach> getAllAttachGroup();

}
