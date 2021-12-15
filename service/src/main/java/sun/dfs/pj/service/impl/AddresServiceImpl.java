package sun.dfs.pj.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.domain.upload.FastImageFile;
import com.github.tobato.fastdfs.exception.FdfsUploadImageException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import jdk.jshell.spi.ExecutionControl;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.dfs.pj.mapper.AddresMapper;
import sun.dfs.pj.model.Addres;
import sun.dfs.pj.model.EsDao;
import sun.dfs.pj.model.User;
import sun.dfs.pj.service.AddresService;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import sun.dfs.pj.utils.UsersUtils;
import sun.dfs.pj.utils.VedioUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class AddresServiceImpl implements AddresService {
    @Autowired
    AddresMapper addresMapper;
    @Autowired
    protected FastFileStorageClient storageClient;
    @Autowired
    private ThumbImageConfig thumbImageConfig;
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public String getUrl(int id) {
        String addres = addresMapper.getUrl(id);
        return addres;
    }

    @Override
    public Addres getAll(int id) {
        Addres addres = addresMapper.selectById(id);
        return addres;
    }

    @Override
    public Integer delete(int id) {
        int i = addresMapper.deleteById(id);
        log.info("删除了:" + id);
        return i;
    }

    @Override
    public String getThumbnail(int id) {
        String url = getUrl(id);
        String thumbImagePath = thumbImageConfig.getThumbImagePath(url);
        return thumbImagePath;
    }

    @Override
    public int addAddres(Addres addres) {
        int insert = addresMapper.insert(addres);
        return insert;
    }

    /**
     * 上传文件
     *
     * @param multipartFile
     * @param isOpen
     * @param metaData
     * @return
     */
    @Override
    public Addres UploadFile(MultipartFile multipartFile, boolean isOpen, String title, Set<MetaData> metaData) {
        //上传文件
        Addres addres = new Addres();
        addres = upfile(multipartFile, isOpen, addres, metaData, title);
        //存入数据库
        int insert = 0;
        try {
            insert = addresMapper.insert(addres);
        } catch (Exception e) {
            storageClient.deleteFile(addres.getUrl());
            e.printStackTrace();
            return null;
        }
        try {
            log.info("用户: " + UsersUtils.getUser() + "进行上传数据库");
            //添加es 方便搜索
            EsDao esDao = converEsDao(addres);
            IndexRequest indexRequest = new IndexRequest("fastdfs");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            String s = objectMapper.writeValueAsString(esDao);
            indexRequest.source(s, XContentType.JSON);
            indexRequest.timeout("10m");
            IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            storageClient.deleteFile(addres.getUrl());
            addresMapper.deleteById(addres.getId());
            log.error("添加文件: " + addres.getUrl() + "失败!!!!");
            e.printStackTrace();
            return null;
        }
        addres.setId(insert);
        log.info("用户: " + UsersUtils.getUser() + "上传文件完毕,类型为: " + addres.getUrl());
        return addres;
    }

    private EsDao converEsDao(Addres addres) {
        EsDao esDao = new EsDao();
        esDao.setDescription(addres.getDescription());
        esDao.setUrl(addres.getUrl());
        esDao.setThumbnail(addres.getThumbnail());
        esDao.setBelong(addres.getBelong());
        esDao.setUid(addres.getId());
        esDao.setFileType(addres.getFileType());
        esDao.setAnnotation(addres.getAnnotation());
        esDao.setOpen(addres.isOpen());
        esDao.setAnnotation(addres.getAnnotation());
        return esDao;
    }


    @Override
    public PageInfo<Addres> publicPage(int current, int size) {
        PageHelper.startPage(current, size);
        List<Addres> publicPage = addresMapper.getPublicPage();
        PageInfo<Addres> pageInfo = new PageInfo<>(publicPage);
        pageInfo.setNavigatePages(5);
        return pageInfo;
    }

    /**
     * 待实现 需要整合spring security +jwt
     *
     * @param current
     * @param size
     * @return
     */
//    @Override
//    @PreAuthorize(value = "")
//    public Page<Addres> pageAll(int current, int size) {
//    /*    Page page = new Page(current,size);
//        addresMapper.selectPage(page, null);*/
//        return null;
//    }
    @Override
    public PageInfo<EsDao> esPage(int current, int size, String value, int Type,boolean open) {
        SearchRequest searchRequest = new SearchRequest("fastdfs");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //设置分页
        searchSourceBuilder.from(current-1).size(size);
        //查询  1 用户  2 文件
        String name = "";
        if (Type == 1) {
            name = "belong";
        } else if (Type == 2) {
            name = "description";
        } else {
            throw new RuntimeException("异常喽!!! 查询需要指定类型");
        }

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        List<QueryBuilder> should = boolQueryBuilder.must();
        if(open)
        should.add(QueryBuilders.termQuery("open",open));

        should.add(QueryBuilders.matchQuery(name, value));
        SearchSourceBuilder query = searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(query);
        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PageInfo<EsDao> pageInfo = new PageInfo<>();
        //将查询到的数据转换为list
        List<EsDao> list = dataConverList(search);
        long value1 = search.getHits().getTotalHits().value; //总数
        pageInfo.setTotal(value1);
        pageInfo.setPageNum(current);  //当前页面
        pageInfo.setPageSize(size);   //页面大小
        int pages = (int) Math.ceil((double) value1 / size);
        pageInfo.setPages(pages);
        int nums[] = null;
        if (pages <= 5) {
            nums = new int[pages];
            for (int i = 0; i < pages; i++) {
                nums[i] = i+1;
            }
        }
        else if (current<=2){
            nums = new int[5];
            for (int i = 0; i < 5; i++) {
                nums[i] = i+1;
            }
        }
        else if (pages - current >= 2) {
            nums = new int[5];
            nums[0] = current - 2;
            nums[1] = current - 1;
            nums[2] = current;
            nums[3] = current + 1;
            nums[4] = current + 2;
        } else {
            nums = new int[5];
            nums[0] = pages - 2;
            nums[1] = pages - 1;
            nums[2] = pages;
            nums[3] = pages + 1;
            nums[4] = pages + 2;
        }
        pageInfo.setNavigatepageNums(nums);
        pageInfo.setList(list);
        return pageInfo;
    }

    private List<EsDao> dataConverList(SearchResponse search) {
        List<EsDao> list = new ArrayList();
        SearchHits hits = search.getHits();
        SearchHit[] hits1 = hits.getHits();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        for (SearchHit documentFields : hits1) {
            String sourceAsString = documentFields.getSourceAsString();
            try {
                EsDao esDao = objectMapper.readValue(sourceAsString, EsDao.class);
                list.add(esDao);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    /**
     * 获取某个用户地址分页
     */
    @Override
    public PageInfo<Addres> getPageAddres(User user, int current, int size) {

        PageHelper.startPage(current, size);
        List<Addres> publicPage = addresMapper.getUserPage(user.getUsername());
        PageInfo<Addres> pageInfo = new PageInfo<>(publicPage);
        pageInfo.setNavigatePages(5);
        return pageInfo;
    }

    /**
     * 获取所有公共文件分页
     */
    public PageInfo<Addres> getAllPage(User user, int current, int size) {
        Page<Object> objects = PageHelper.startPage(current, size);
        List<Addres> publicPage = addresMapper.getPublicPage();
        PageInfo<Addres> pageInfo = new PageInfo<>(publicPage);
        pageInfo.setNavigatePages(5);
        return pageInfo;
    }

    private Addres upfile(MultipartFile multipartFile, boolean isOpen, Addres addres, Set<MetaData> metaData, String title) {
        String name = multipartFile.getOriginalFilename();
        String substring = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
        log.info("用户: " + UsersUtils.getUser() + " 进行上传文件,类型为: " + substring);
        StorePath storePath = null;
        InputStream[] inputStreams;
        String thumbnailPath = "";
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //如果为视频上传缩略图
        if (VedioUtils.videoTypeContains(substring)) {
            //拷贝两个输入流
            inputStreams = VedioUtils.copyInputStream(inputStream, 2);
            //上传图片
            try {
                storePath = storageClient.uploadFile(inputStreams[0], multipartFile.getSize(), substring, metaData);
            } catch (FdfsUploadImageException ex) {

            }
            //进行获取视频缩略图
            BufferedImage videoThumbnail = VedioUtils.getVideoThumbnail(inputStreams[1]);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                ImageIO.write(videoThumbnail, "png", os);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(os.toByteArray());
            //进行缩略图上传
            thumbnailPath = storageClient.uploadFile(byteArrayInputStream, os.size(), "png", metaData).getFullPath();
            addres.setAnnotation("video");
        } else if (VedioUtils.imageTypeContains(substring)) { //如果为图片 保存缩略图
            FastImageFile build = new FastImageFile.Builder().withFile(inputStream, multipartFile.getSize(), substring).build();
            storePath = storageClient.uploadImage(build);
            thumbnailPath = storePath.getFullPath();
            addres.setAnnotation("image");
        } else { //其他文件设置为空
            storePath = storageClient.uploadFile(inputStream, multipartFile.getSize(), substring.toLowerCase(), metaData);
            addres.setAnnotation("other");
        }
        String fullPath = storePath.getFullPath();
        addres.setBelong(UsersUtils.getUser().getUsername());
        addres.setUrl(fullPath);
        addres.setOpen(isOpen);
        addres.setUid(UsersUtils.getUser().getId());
        addres.setThumbnail(thumbnailPath);
        addres.setFileType(substring);
        addres.setOuther(1);
        addres.setDescription(title);
        return addres;
    }
}
