// src/main/kotlin/Application.kt
package com.devmoekyawaung

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.html.*
import io.ktor.http.*
import kotlinx.html.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        routing {
            get("/") {
                call.respondHtml {
                    portfolioPage()
                }
            }
            get("/api/projects") {
                call.respond(getProjects())
            }
            get("/api/theme/{theme}") {
                val theme = call.parameters["theme"] ?: "light"
                call.respond(mapOf("theme" to theme))
            }
        }
    }.start(wait = true)
}

fun HTML.portfolioPage() {
    head {
        meta(charset = "utf-8")
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
        title("Dev Moe Kyawaung - Kotlin Portfolio")
        style {
            unsafe {
                raw("""
                    :root {
                        --primary: #667eea;
                        --secondary: #764ba2;
                        --bg-light: #ffffff;
                        --bg-dark: #1a1a1a;
                        --text-light: #333333;
                        --text-dark: #ffffff;
                    }

                    * {
                        margin: 0;
                        padding: 0;
                        box-sizing: border-box;
                    }

                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        background: linear-gradient(135deg, var(--primary) 0%, var(--secondary) 100%);
                        color: var(--text-light);
                        transition: all 0.3s ease;
                        line-height: 1.6;
                    }

                    body.dark-mode {
                        background: linear-gradient(135deg, #1a1a2e 0%, #16213e 100%);
                        color: var(--text-dark);
                    }

                    .container {
                        max-width: 1200px;
                        margin: 0 auto;
                        padding: 0 20px;
                    }

                    /* Header */
                    header {
                        background: rgba(255, 255, 255, 0.95);
                        backdrop-filter: blur(10px);
                        position: sticky;
                        top: 0;
                        z-index: 100;
                        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                        transition: all 0.3s ease;
                    }

                    body.dark-mode header {
                        background: rgba(26, 26, 26, 0.95);
                        box-shadow: 0 2px 10px rgba(0,0,0,0.5);
                    }

                    nav {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        padding: 1rem 0;
                    }

                    .logo {
                        font-size: 1.5rem;
                        font-weight: 700;
                        background: linear-gradient(135deg, #667eea, #764ba2);
                        -webkit-background-clip: text;
                        -webkit-text-fill-color: transparent;
                    }

                    .nav-links {
                        display: flex;
                        list-style: none;
                        gap: 2rem;
                        align-items: center;
                    }

                    .nav-links a {
                        text-decoration: none;
                        color: #333;
                        font-weight: 500;
                        transition: all 0.3s ease;
                        position: relative;
                    }

                    body.dark-mode .nav-links a {
                        color: #fff;
                    }

                    .nav-links a::after {
                        content: '';
                        position: absolute;
                        bottom: -5px;
                        left: 0;
                        width: 0;
                        height: 2px;
                        background: #667eea;
                        transition: width 0.3s ease;
                    }

                    .nav-links a:hover::after {
                        width: 100%;
                    }

                    .theme-toggle {
                        background: #667eea;
                        color: white;
                        border: none;
                        padding: 8px 16px;
                        border-radius: 50px;
                        cursor: pointer;
                        transition: all 0.3s ease;
                        font-weight: 600;
                    }

                    .theme-toggle:hover {
                        transform: scale(1.05);
                        box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
                    }

                    /* Hero Section */
                    .hero {
                        min-height: 100vh;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        color: white;
                        text-align: center;
                        padding: 2rem 0;
                    }

                    .hero-content h1 {
                        font-size: 4rem;
                        margin-bottom: 1rem;
                        animation: slideInDown 0.8s ease;
                    }

                    .hero-content p {
                        font-size: 1.5rem;
                        margin-bottom: 2rem;
                        opacity: 0.9;
                        animation: slideInUp 0.8s ease 0.2s both;
                    }

                    .hero-buttons {
                        display: flex;
                        gap: 1rem;
                        justify-content: center;
                        animation: fadeIn 1s ease 0.4s both;
                    }

                    .btn {
                        padding: 12px 30px;
                        border: none;
                        border-radius: 50px;
                        font-size: 1rem;
                        font-weight: 600;
                        cursor: pointer;
                        transition: all 0.3s ease;
                        text-decoration: none;
                        display: inline-block;
                    }

                    .btn-primary {
                        background: white;
                        color: #667eea;
                    }

                    .btn-primary:hover {
                        transform: translateY(-3px);
                        box-shadow: 0 10px 30px rgba(0,0,0,0.2);
                    }

                    .btn-secondary {
                        background: transparent;
                        color: white;
                        border: 2px solid white;
                    }

                    .btn-secondary:hover {
                        background: white;
                        color: #667eea;
                        transform: translateY(-3px);
                    }

                    /* About Section */
                    .about {
                        padding: 5rem 0;
                        background: white;
                        transition: all 0.3s ease;
                    }

                    body.dark-mode .about {
                        background: #222;
                    }

                    .section-title {
                        font-size: 2.5rem;
                        text-align: center;
                        margin-bottom: 3rem;
                        color: #333;
                        position: relative;
                        transition: all 0.3s ease;
                    }

                    body.dark-mode .section-title {
                        color: white;
                    }

                    .section-title::after {
                        content: '';
                        position: absolute;
                        bottom: -15px;
                        left: 50%;
                        transform: translateX(-50%);
                        width: 60px;
                        height: 3px;
                        background: linear-gradient(135deg, #667eea, #764ba2);
                    }

                    .about-content {
                        display: grid;
                        grid-template-columns: 1fr 1fr;
                        gap: 3rem;
                        align-items: center;
                        margin-top: 3rem;
                    }

                    .gravatar {
                        width: 250px;
                        height: 250px;
                        border-radius: 50%;
                        box-shadow: 0 10px 40px rgba(0,0,0,0.2);
                        margin: 0 auto;
                        display: block;
                        transition: transform 0.3s ease;
                        animation: float 3s ease-in-out infinite;
                    }

                    .gravatar:hover {
                        transform: scale(1.05);
                    }

                    .about-text p {
                        color: #666;
                        margin-bottom: 1.5rem;
                        font-size: 1.1rem;
                        transition: all 0.3s ease;
                    }

                    body.dark-mode .about-text p {
                        color: #ccc;
                    }

                    .skills {
                        display: flex;
                        gap: 1rem;
                        flex-wrap: wrap;
                        margin-top: 1.5rem;
                    }

                    .skill-tag {
                        background: #667eea;
                        color: white;
                        padding: 0.5rem 1rem;
                        border-radius: 20px;
                        font-size: 0.9rem;
                        transition: all 0.3s ease;
                    }

                    .skill-tag:hover {
                        background: #764ba2;
                        transform: translateY(-3px);
                        box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
                    }

                    /* Projects Section */
                    .projects {
                        padding: 5rem 0;
                        background: white;
                        transition: all 0.3s ease;
                    }

                    body.dark-mode .projects {
                        background: #222;
                    }

                    .project-grid {
                        display: grid;
                        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
                        gap: 2rem;
                        margin-top: 3rem;
                    }

                    .project-card {
                        background: #f8f9fa;
                        padding: 2rem;
                        border-radius: 15px;
                        transition: all 0.3s ease;
                        border: 2px solid transparent;
                        cursor: pointer;
                    }

                    body.dark-mode .project-card {
                        background: #333;
                    }

                    .project-card:hover {
                        transform: translateY(-10px);
                        box-shadow: 0 20px 50px rgba(0,0,0,0.15);
                        border-color: #667eea;
                    }

                    .project-icon {
                        font-size: 3rem;
                        margin-bottom: 1rem;
                        animation: bounce 2s ease-in-out infinite;
                    }

                    .project-card h3 {
                        color: #333;
                        margin-bottom: 0.5rem;
                        transition: all 0.3s ease;
                    }

                    body.dark-mode .project-card h3 {
                        color: white;
                    }

                    .project-card p {
                        color: #666;
                        margin-bottom: 1.5rem;
                        transition: all 0.3s ease;
                    }

                    body.dark-mode .project-card p {
                        color: #aaa;
                    }

                    .project-link {
                        color: #667eea;
                        text-decoration: none;
                        font-weight: 600;
                        display: inline-flex;
                        align-items: center;
                        gap: 0.5rem;
                        transition: all 0.3s ease;
                    }

                    .project-link:hover {
                        gap: 1rem;
                    }

                    /* Contact Section */
                    .contact {
                        padding: 5rem 0;
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        color: white;
                        text-align: center;
                    }

                    .contact .section-title {
                        color: white;
                    }

                    .contact .section-title::after {
                        background: white;
                    }

                    .contact-links {
                        display: flex;
                        gap: 2rem;
                        justify-content: center;
                        flex-wrap: wrap;
                        margin-top: 3rem;
                    }

                    .contact-links a {
                        color: white;
                        text-decoration: none;
                        padding: 12px 30px;
                        border: 2px solid white;
                        border-radius: 50px;
                        transition: all 0.3s ease;
                        display: inline-block;
                    }

                    .contact-links a:hover {
                        background: white;
                        color: #667eea;
                        transform: translateY(-3px);
                        box-shadow: 0 10px 30px rgba(0,0,0,0.2);
                    }

                    /* Footer */
                    footer {
                        background: #222;
                        color: white;
                        text-align: center;
                        padding: 2rem;
                        transition: all 0.3s ease;
                    }

                    body.dark-mode footer {
                        background: #111;
                    }

                    /* Animations */
                    @keyframes slideInDown {
                        from {
                            opacity: 0;
                            transform: translateY(-30px);
                        }
                        to {
                            opacity: 1;
                            transform: translateY(0);
                        }
                    }

                    @keyframes slideInUp {
                        from {
                            opacity: 0;
                            transform: translateY(30px);
                        }
                        to {
                            opacity: 1;
                            transform: translateY(0);
                        }
                    }

                    @keyframes fadeIn {
                        from {
                            opacity: 0;
                        }
                        to {
                            opacity: 1;
                        }
                    }

                    @keyframes float {
                        0%, 100% {
                            transform: translateY(0px);
                        }
                        50% {
                            transform: translateY(-20px);
                        }
                    }

                    @keyframes bounce {
                        0%, 100% {
                            transform: translateY(0);
                        }
                        50% {
                            transform: translateY(-10px);
                        }
                    }

                    /* Responsive */
                    @media (max-width: 768px) {
                        .hero-content h1 {
                            font-size: 2.5rem;
                        }

                        .about-content {
                            grid-template-columns: 1fr;
                        }

                        .nav-links {
                            gap: 1rem;
                        }

                        .hero-buttons {
                            flex-direction: column;
                        }

                        .btn {
                            width: 100%;
                        }
                    }
                """.trimIndent())
            }
        }
    }
    body {
        id = "app-body"
        // Header
        header {
            div("container") {
                nav {
                    div("logo") { +"✨ Dev Moe Kyawaung" }
                    ul("nav-links") {
                        li { a(href = "#home") { +"Home" } }
                        li { a(href = "#about") { +"About" } }
                        li { a(href = "#projects") { +"Projects" } }
                        li { a(href = "#contact") { +"Contact" } }
                        li { 
                            button(type = ButtonType.button) {
                                id = "theme-toggle"
                                classes = setOf("theme-toggle")
                                +"🌙 Dark"
                            }
                        }
                    }
                }
            }
        }

        // Hero
        section("hero") {
            id = "home"
            div("container") {
                div("hero-content") {
                    h1 { +"Dev Moe Kyawaung" }
                    p("hero-subtitle") { +"Full Stack Developer | Kotlin Expert | Backend Specialist" }
                    div("hero-buttons") {
                        a(href = "#projects", classes = "btn btn-primary") { +"View Projects" }
                        a(href = "#contact", classes = "btn btn-secondary") { +"Get In Touch" }
                    }
                }
            }
        }

        // About
        section("about") {
            id = "about"
            div("container") {
                h2("section-title") { +"About Me" }
                div("about-content") {
                    div {
                        img(src = "https://gravatar.com/avatar/moekyawaung2026?s=300&d=identicon", alt = "Profile", classes = "gravatar")
                    }
                    div("about-text") {
                        p {
                            +"I'm a passionate backend developer with deep expertise in Kotlin and Ktor framework. "
                            +"I specialize in building scalable, high-performance APIs and microservices."
                        }
                        p {
                            +"With a strong foundation in software architecture and best practices, "
                            +"I create robust solutions for complex business problems."
                        }
                        div("skills") {
                            span("skill-tag") { +"Kotlin" }
                            span("skill-tag") { +"Ktor" }
                            span("skill-tag") { +"Spring Boot" }
                            span("skill-tag") { +"PostgreSQL" }
                            span("skill-tag") { +"Microservices" }
                            span("skill-tag") { +"Docker" }
                        }
                    }
                }
            }
        }

        // Projects
        section("projects") {
            id = "projects"
            div("container") {
                h2("section-title") { +"Featured Projects" }
                div("project-grid") {
                    projectCard("🚀", "REST API Server", "High-performance REST API built with Ktor and Kotlin")
                    projectCard("💾", "Database Migration Tool", "Automated database schema migration system")
                    projectCard("🔐", "Authentication Service", "Secure authentication and authorization system")
                    projectCard("📊", "Real-time Analytics", "Event streaming and analytics platform")
                    projectCard("🔄", "Message Queue System", "Distributed message processing system")
                    projectCard("⚙️", "Configuration Server", "Centralized configuration management service")
                }
            }
        }

        // Contact
        section("contact") {
            id = "contact"
            div("container") {
                h2("section-title") { +"Let's Connect" }
                p { +"Feel free to reach out through any of these platforms" }
                div("contact-links") {
                    a(href = "https://github.com/Dev-moe-kyawaung", target = "_blank") { +"GitHub" }
                    a(href = "https://gravatar.com/moekyawaung2026", target = "_blank") { +"Gravatar" }
                    a(href = "https://gravatar.com/moekyawaung13721", target = "_blank") { +"Profile 2" }
                    a(href = "mailto:moekyawaung@example.com") { +"Email" }
                }
            }
        }

        // Footer
        footer {
            p { +"© 2024 Dev Moe Kyawaung. Built with Kotlin + Ktor." }
        }

        script {
            unsafe {
                raw("""
                    const themeToggle = document.getElementById('theme-toggle');
                    const body = document.getElementById('app-body');
                    
                    // Check for saved theme preference or default to light mode
                    const currentTheme = localStorage.getItem('theme') || 'light';
                    if (currentTheme === 'dark') {
                        body.classList.add('dark-mode');
                        themeToggle.textContent = '☀️ Light';
                    }
                    
                    themeToggle.addEventListener('click', () => {
                        body.classList.toggle('dark-mode');
                        const theme = body.classList.contains('dark-mode') ? 'dark' : 'light';
                        localStorage.setItem('theme', theme);
                        themeToggle.textContent = theme === 'dark' ? '☀️ Light' : '🌙 Dark';
                    });

                    // Smooth scroll
                    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
                        anchor.addEventListener('click', function (e) {
                            e.preventDefault();
                            const target = document.querySelector(this.getAttribute('href'));
                            if (target) {
                                target.scrollIntoView({ behavior: 'smooth' });
                            }
                        });
                    });
                """.trimIndent())
            }
        }
    }
}

fun DIV.projectCard(icon: String, title: String, description: String) {
    div("project-card") {
        div("project-icon") { +icon }
        h3 { +title }
        p { +description }
        a(href = "#", classes = "project-link") { +"Learn More →" }
    }
}

data class Project(
    val id: Int,
    val title: String,
    val description: String,
    val icon: String
)

fun getProjects(): List<Project> = listOf(
    Project(1, "REST API Server", "High-performance REST API built with Ktor and Kotlin", "🚀"),
    Project(2, "Database Migration Tool", "Automated database schema migration system", "💾"),
    Project(3, "Authentication Service", "Secure authentication and authorization system", "🔐"),
    Project(4, "Real-time Analytics", "Event streaming and analytics platform", "📊"),
    Project(5, "Message Queue System", "Distributed message processing system", "🔄"),
    Project(6, "Configuration Server", "Centralized configuration management service", "⚙️")
)
