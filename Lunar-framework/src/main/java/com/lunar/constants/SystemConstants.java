package com.lunar.constants;

public class SystemConstants {
    /**
     * 热门博客展示当前页数
     */
    public static final int HOT_BLOG_PAGE_NUMBER = 1;
    /**
     * 热门博客展示分页大小
     */
    public static final int HOT_BLOG_PAGE_SIZE = 10;
    /**
     * 根据标签搜索博客展示分页默认页数
     */
    public static final int TAG_BLOG_PAGE_NUMBER_DEFAULT = 1;
    /**
     * 根据标签搜索博客展示分页大小
     */
    public static final int TAG_BLOG_PAGE_SIZE = 10;
    /**
     * 博客可见性为公开
     */
    public static final int BLOG_FORM_PUBLIC = 0;
    /**
     * 博客可见性为仅粉丝可见
     */
    public static final int BLOG_FORM_ONLY_FANS = 1;
    /**
     * 博客可见性为私有
     */
    public static final int BLOG_FORM_ONLY_AUTHOR = 2;
    /**
     * 登录后存储在redis中的key 后加userId
     */
    public static final String REDIS_CACHE_LOGIN_KEY = "lunarLogin:";
}
