package hkmu.edu.hk.s380f.noot.controller;

public class UserProfileController {

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String overviewEmployee(Model model) {
        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("userSectionService", userSectionService);
        return "user-list";
    }

    @RequestMapping(value = "/overviewUser", method = RequestMethod.GET)
    public String overviewUser(Model model, @RequestParameter long id) {
        model.addAttribute("currentUser", userService.getById(id));
        return "overviewUser";
    }
}


<body>
    <div>
        <c:each="section : ${sections}">
        <a href="overviewDivision.html?id=1" a href="@{/overviewDivision?id=1}">
        <span th:text="${section.name}">User</span></a>
    </div>
    <div>
        <c:each="user : ${userSectionService.getUsersBySectionId(section.id)}">
        <a href="overviewUser.html?id= + user.id" th:href="@{/overviewUser?id= + user.id}">
        <span th:text="${user.userName + ' ' + user.lastName + user.body ' '+ user.attachments}"></span></a>
    </div>
