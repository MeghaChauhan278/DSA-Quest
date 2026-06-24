-- ==========================================
-- DATABASE SCHEMA (MySQL)
-- ==========================================

CREATE DATABASE IF NOT EXISTS dsa_quest;
USE dsa_quest;

-- Drop tables if they exist to start fresh
DROP TABLE IF EXISTS user_achievements;
DROP TABLE IF EXISTS solved_problems;
DROP TABLE IF EXISTS achievements;
DROP TABLE IF EXISTS users;

-- 1. Users Table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    total_xp INT DEFAULT 0,
    current_level INT DEFAULT 1,
    current_streak INT DEFAULT 0,
    longest_streak INT DEFAULT 0,
    total_problems_solved INT DEFAULT 0,
    join_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 2. Achievements Table
CREATE TABLE achievements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    xp_reward INT DEFAULT 0
);

-- 3. Solved Problems Table
CREATE TABLE solved_problems (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    title VARCHAR(255) NOT NULL,
    problem_link VARCHAR(255),
    difficulty ENUM('Easy', 'Medium', 'Hard'),
    topic VARCHAR(100),
    platform VARCHAR(100),
    solved_date DATE,
    xp_earned INT,
    notes TEXT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 4. User Achievements (Mapping Table)
CREATE TABLE user_achievements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    achievement_id BIGINT,
    earned_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (achievement_id) REFERENCES achievements(id) ON DELETE CASCADE
);

-- ==========================================
-- SAMPLE DATA SCRIPT
-- ==========================================

-- Sample Achievements
INSERT INTO achievements (name, description, xp_reward) VALUES 
('First Step', 'Solve your first DSA problem.', 10),
('Decathlon', 'Solve 10 DSA problems.', 50),
('Half Century', 'Solve 50 DSA problems.', 150),
('Centurion', 'Solve 100 DSA problems.', 300),
('Consistency King', 'Maintain a 7-day streak.', 100),
('Monthly Master', 'Maintain a 30-day streak.', 500),
('Hardcore Solver', 'Solve 10 Hard difficulty problems.', 500),
('Topic Master: Arrays', 'Solve 15 problems related to Arrays.', 200);

-- Sample User (Password is 'password' - placeholder)
INSERT INTO users (username, email, password, total_xp, current_level, total_problems_solved) VALUES 
('dsa_warrior', 'warrior@example.com', '$2a$10$8.Kclm3spQD18.6QGZpCOu3gZ.iQ3d3cXZJz2pGZJb4oOq6m.wRie', 50, 1, 3);

-- Sample Solved Problems for 'dsa_warrior'
INSERT INTO solved_problems (user_id, title, problem_link, difficulty, topic, platform, solved_date, xp_earned, notes) VALUES 
(1, 'Two Sum', 'https://leetcode.com/problems/two-sum/', 'Easy', 'Array', 'LeetCode', CURDATE(), 10, 'Used HashMap for O(n) solution.'),
(1, 'Reverse Linked List', 'https://leetcode.com/problems/reverse-linked-list/', 'Easy', 'Linked List', 'LeetCode', DATE_SUB(CURDATE(), INTERVAL 1 DAY), 10, 'Iterative approach works well.'),
(1, 'Longest Substring Without Repeating Characters', 'https://leetcode.com/problems/longest-substring-without-repeating-characters/', 'Medium', 'Sliding Window', 'LeetCode', DATE_SUB(CURDATE(), INTERVAL 2 DAY), 25, 'Sliding window with character map.');

-- Sample User Achievement
INSERT INTO user_achievements (user_id, achievement_id) VALUES (1, 1);
