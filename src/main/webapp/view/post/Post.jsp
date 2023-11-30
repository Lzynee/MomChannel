<%--
  Created by IntelliJ IDEA.
  User: sec
  Date: 2023-11-24
  Time: 오후 3:51
  Description: 학년별 게시글 목록 보기를 구현하는 뷰
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>맘채널</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/froala_style.css"
        type="text/css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"
        type="text/css">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&family=
  Public+Sans&display=swap" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.min.js" integrity="sha384-Rx+T1VzGupg4BHQYs2gCW9It+akI2MM/mndMCy36UVfodzcJcF0GGLxZIzObiEfa" crossorigin="anonymous"></script>
  <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
  
</head>
<body>
<%@ include file="../include/header.jsp"%>
          <%-- 게시글 목록을 구현한다. --%>
          <div class="content_card_area">
            <a href="./write.do">글쓰기
            <div class="inner">
              <ul class="card_area">  <%-- 게시글들을 배치 --%>
                <c:choose>
                  <c:when test="${ empty postLists }">  <%-- 게시물이 없을 때 --%>
                    <p>등록된 게시물이 없습니다 ☺️</p>
                  </c:when>
                  <c:otherwise>  <%-- 게시물이 있을 때 --%>
                    <%-- 게시물이 있으면 목록에 출력할 가상번호를 계산하고, 반복 출력한다. --%>
                    <c:forEach items="${ postLists }" var="row" varStatus="loop">
                     <li class="content_card">  <%-- 개별 게시글을 카드 형태로 노출 --%>
                        <a href="${pageContext.request.contextPath}/view/post/postview.do?postNo=${ row.postNo }">  <%-- 게시글로 이동하는 링크 --%>
                          <figure class="content_img"></figure>
                          <div class="text_area">
                          <%-- 제목(상세보기 페이지로 바로가기 링크) --%>
                          <%-- 게시물의 일련번호를 매개변수로 사용한다. --%>
                            <a class="content_name"
                              href="${pageContext.request.contextPath}/view/post/postview.do?postNo=${ row.postNo }"
                              style="text-decoration: none; color: black;">
                              ${ row.postTitle }
                            </a>
                            <p class="user_name"><i></i><span>${ row.memId }</span></p>
                          </div>
                        </a>
                        <a href="#">${ row.postLikes }</a>  <%-- 좋아요 버튼 --%>
                        <div class="tag_area">  <%-- 게시글 태그 --%>
                          <span class="badge">${ row.postRegion }</span>
                          <span class="badge">${ row.postGrade }</span>
                        </div>
                      </c:forEach>
                    </c:otherwise>
                  </c:choose>
                </li>
              </ul>
              <div class="pagination-module">
                <nav aria-level="Page navigation">
                  <ul class="pagination">
                    <li class="page-item">
                      <a href="#" class="page-link" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                      </a></li>
                    <li class="page-item">
                      <a class="page-link" href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                      </a>
                    </li>
                  </ul>
                </nav>
              </div>
            </div>
          </div>
<%@ include file="../include/footer.jsp"%>
