/*
 * Copyright (c) 2011 Aurélien VIALE
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package fr.soat.devoxx.game.tools;

public interface TilesUtil {

    /* IndexController Pages */
    public static final String DFR_INDEX_PAGE = "devoxxfr.index";
    
    /* AdminController Pages */
    public static final String DFR_ADMIN_INDEX_PAGE = "devoxxfr.admin.index";
    
    /* AdminUserController Pages */
    public static final String DFR_ADMIN_SHOWALLUSERS_PAGE = "devoxxfr.admin.showallusers";
    public static final String DFR_ADMIN_SHOWUSER_PAGE = "devoxxfr.admin.showuser";
    public static final String DFR_ADMIN_UPDATEUSER_PAGE = "devoxxfr.admin.updateuser";
    
    /* AdminQuestionController Pages */    
    public static final String DFR_ADMIN_IMPORTQUESTIONS_PAGE = "devoxxfr.admin.importquestions";
    public static final String DFR_ADMIN_SHOWQUESTION_PAGE = "devoxxfr.admin.showquestion";
    
    /* AuthController Pages */
    public static final String DFR_AUTH_LOGIN_PAGE = "devoxxfr.auth.login";
    public static final String DFR_AUTH_MOBILE_LOGIN_PAGE = "devoxxfr.mobile.auth.login";
    
    /* Common pages */
    public static final String DFR_NOT_FOUND_PAGE = "devoxxfr.notfound";
    
    /* Errors pages */
    public static final String DFR_ERRORS_ERRORMSG_PAGE = "devoxxfr.errors.errormsg";
    public static final String DFR_ERRORS_404_PAGE = "devoxxfr.errors.notfound";
    public static final String DFR_ERRORS_403_PAGE = "devoxxfr.errors.forbidden";
    public static final String DFR_MOBILE_ERRORS_404_PAGE = "devoxxfr.mobile.errors.notfound";
    public static final String DFR_MOBILE_ERRORS_403_PAGE = "devoxxfr.mobile.errors.forbidden";
    
    /* Game pages */
    public static final String DFR_GAME_INDEX_PAGE = "devoxxfr.game.index";
    public static final String DFR_GAME_PLAY_PAGE = "devoxxfr.game.play";
    public static final String DFR_GAME_ANSWER_PAGE = "devoxxfr.game.answer";
    public static final String DFR_GAME_RULES_APPROVAL = "devoxxfr.game.approval";
    public static final String DFR_GAME_RULES_PAGE = "devoxxfr.game.rules";
    public static final String DFR_GAME_ABOUT_PAGE = "devoxxfr.game.about";
    public static final String DFR_GAME_RANKING_PAGE = "devoxxfr.game.ranking";
}
