"""
Title: IMDB Top Movies Data Scraper
Author: 田一然 (22373006)
Date: 2024.11.23
Description: This script scrapes the IMDB Top Movies page to extract detailed information
             about the movies, including title, description, image URL, content rating,
             duration, release year, directors, actors, and genres, and saves this data
             into multiple CSV files.
"""

import json
import requests
from bs4 import BeautifulSoup
import csv
import re

# 发送HTTP请求获取IMDB排行榜的网页内容
url = 'https://www.imdb.com/chart/top'
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3'}
response = requests.get(url, headers=headers)

if response.status_code == 200:
    print("Successfully fetched the web page.")
    # 解析HTML内容
    soup = BeautifulSoup(response.content, 'html.parser')

    # 查找嵌入的JSON数据
    script_tag = soup.find('script', type='application/json', id='__NEXT_DATA__')
    if script_tag:
        json_data = script_tag.string.strip()
        data = json.loads(json_data)
        movies = data['props']['pageProps']['pageData']['chartTitles']['edges']

        # 打开CSV文件，用于存储数据
        with open('movies.csv', 'w', newline='', encoding='utf-8') as movies_file, \
             open('ratings.csv', 'w', newline='', encoding='utf-8') as ratings_file, \
             open('directors.csv', 'w', newline='', encoding='utf-8') as directors_file, \
             open('actors.csv', 'w', newline='', encoding='utf-8') as actors_file, \
             open('genres.csv', 'w', newline='', encoding='utf-8') as genres_file:

            # 创建CSV写入器
            movies_writer = csv.writer(movies_file)
            ratings_writer = csv.writer(ratings_file)
            directors_writer = csv.writer(directors_file)
            actors_writer = csv.writer(actors_file)
            genres_writer = csv.writer(genres_file)

            # 写入CSV标题行
            movies_writer.writerow(['movie_id', 'title', 'description', 'image_url', 'content_rating', 'duration', 'release_year'])
            ratings_writer.writerow(['movie_id', 'rating', 'rating_count'])
            directors_writer.writerow(['movie_id', 'director'])
            actors_writer.writerow(['movie_id', 'actor'])
            genres_writer.writerow(['movie_id', 'genre'])

            # 迭代每部电影的信息
            for movie in movies:
                movie_id = movie['node']['id']
                title = movie['node']['titleText']['text']
                description = movie['node']['plot']['plotText']['plainText'] if 'plot' in movie['node'] else 'N/A'
                image_url = movie['node']['primaryImage']['url']
                rating = movie['node']['ratingsSummary']['aggregateRating']
                rating_count = movie['node']['ratingsSummary']['voteCount']

                # 获取 `contentRating` 字段
                certificate = movie['node'].get('certificate')
                is_ratable = movie['node']['canRate']['isRatable']
                content_rating = certificate['rating'] if is_ratable and certificate and 'rating' in certificate else 'N/A'

                duration = movie['node']['runtime']['seconds'] if 'runtime' in movie['node'] else 'N/A'
                release_year = movie['node']['releaseYear']['year'] if 'releaseYear' in movie['node'] else 'N/A'
                genres = [genre['genre']['text'] for genre in movie['node']['titleGenres']['genres']]
                movie_url = f"https://www.imdb.com/title/{movie_id}/"

                # 请求电影详情页
                movie_response = requests.get(movie_url, headers=headers)
                movie_soup = BeautifulSoup(movie_response.content, 'html.parser')

                # 爬取导演信息
                director_tag = movie_soup.find('a', {'href': re.compile('/name/nm.*')})
                director = director_tag.text.strip() if director_tag else 'N/A'

                # 爬取演员信息，去重并忽略空值
                actors_tags = movie_soup.find_all('a', {'href': re.compile('/name/nm.*')})
                actors = list(set([actor.text.strip() for actor in actors_tags if actor.text.strip() != '']))

                # 写入电影信息到CSV文件
                movies_writer.writerow([movie_id, title, description, image_url, content_rating, duration, release_year])
                ratings_writer.writerow([movie_id, rating, rating_count])
                directors_writer.writerow([movie_id, director])
                for actor in actors:
                    actors_writer.writerow([movie_id, actor])
                for genre in genres:
                    genres_writer.writerow([movie_id, genre])

        print("Data saved to CSV files.")
    else:
        print("No JSON data found in the page.")
else:
    print(f"Failed to fetch the web page. Status code: {response.status_code}")
